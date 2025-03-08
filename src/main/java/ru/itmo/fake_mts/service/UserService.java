package ru.itmo.fake_mts.service;

import ru.itmo.fake_mts.dto.*;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.entity.UserStatus;
import ru.itmo.fake_mts.exception.*;
import ru.itmo.fake_mts.repo.UserRepository;
import ru.itmo.fake_mts.security.JwtService;
import ru.itmo.fake_mts.security.strategy.AuthStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static ru.itmo.fake_mts.entity.AuthMethod.SMS_ONLY;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final SmsService smsService;
    private final CodeStorage codeStorage;
    private final List<AuthStrategy> strategies;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public StartAuthResponse startAuth(String phoneNumber) {
        validatePhoneNumber(phoneNumber);

        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if (user == null) {
            user = User.builder()
                    .phoneNumber(phoneNumber)
                    .authMethod(SMS_ONLY)
                    .status(UserStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
            user = userRepository.save(user);
        }

        AuthMethod method = user.getAuthMethod();

        if (method == SMS_ONLY || method == AuthMethod.PASSWORD_SMS) {
            String code = generateRandomCode();
            codeStorage.saveCodeForPhone(phoneNumber, code);
            smsService.sendSms(phoneNumber, code);
        }

        return StartAuthResponse.builder()
                .message("OK: " + ((method == SMS_ONLY || method == AuthMethod.PASSWORD_SMS)
                        ? "SMS code sent." : "no SMS needed."))
                .authMethod(method.name())
                .build();
    }

    public AuthCompleteResponse completeAuth(String phoneNumber, AuthRequest authRequest) {
        validatePhoneNumber(phoneNumber);

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User with phone " + phoneNumber + " not found"));

        String storedCode = codeStorage.getCodeForPhone(phoneNumber);

        if (user.getAuthMethod() == SMS_ONLY && storedCode == null) {
            throw new WrongPhoneNumberException("No code was found for the specified phone number." +
                    " The code may have been sent to another number.");
        }

        AuthStrategy strategy = strategies.stream()
                .filter(s -> s.getAuthMethod() == user.getAuthMethod())
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("No strategy for: " + user.getAuthMethod()));

        boolean ok = strategy.authenticate(user, authRequest);
        if (!ok) {
            throw new AuthenticationException("Authentication failed for method = " + user.getAuthMethod());
        }

        if (user.getAuthMethod() == SMS_ONLY || user.getAuthMethod() == AuthMethod.PASSWORD_SMS) {
            codeStorage.removeCodeForPhone(phoneNumber);
        }

        String token = jwtService.generateToken(user);
        return AuthCompleteResponse.builder()
                .token(token)
                .build();
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

    public UserResponse patchUser(UserPatchRequest patch) {
        User user = currentUserService.getCurrentUserOrThrow();

        if (patch.getFullName() != null) {
            user.setFullName(patch.getFullName());
        }

        if (patch.getSnils() != null) {
            validateSnils(patch.getSnils());
            user.setSnils(patch.getSnils());
        }

        if (patch.getInn() != null) {
            validateInn(patch.getInn());
            user.setInn(patch.getInn());
        }

        if (patch.getEmailBackup() != null) {
            user.setEmailBackup(patch.getEmailBackup());
        }

        User updatedUser = userRepository.save(user);
        return UserResponse.fromEntity(updatedUser);
    }

    private void validateSnils(String snils) {
        if (!snils.matches("\\d{11}")) {
            throw new InvalidSnilsFormatException("Invalid format of SNILS");
        }
    }

    private void validateInn(String inn) {
        if (!inn.matches("\\d{12}")) {
            throw new InvalidInnFormatException("Invalid format of INN");
        }
    }

    public UserIdResponse changeAuthMethod(ChangeAuthMethodRequest dto) {
        User user = currentUserService.getCurrentUserOrThrow();

        user.setAuthMethod(dto.getNewMethod());
        if ((dto.getNewMethod() == AuthMethod.PASSWORD_ONLY
                || dto.getNewMethod() == AuthMethod.PASSWORD_SMS)
                && dto.getNewPassword() != null)
        {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        userRepository.save(user);
        return new UserIdResponse(user.getId());
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
        }
    }
}