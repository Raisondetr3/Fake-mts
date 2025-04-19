package ru.itmo.node_a_core.service;

import org.springframework.transaction.annotation.Transactional;
import ru.itmo.common.dto.*;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.common.entity.User;
import ru.itmo.common.entity.enums.UserStatus;
import ru.itmo.common.exception.*;
import ru.itmo.common.repo.UserRepository;
import ru.itmo.node_a_core.security.JwtService;
import ru.itmo.node_a_core.security.strategy.AuthStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static ru.itmo.common.entity.enums.AuthMethod.SMS_ONLY;

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

    @Transactional
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

    public AuthCompleteResponse completeAuth(CompleteAuthRequest request) {
        validatePhoneNumber(request.getPhoneNumber());

        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("User with phone " + request.getPhoneNumber() + " not found"));

        String storedCode = codeStorage.getCodeForPhone(request.getPhoneNumber());

        if (user.getAuthMethod() == AuthMethod.SMS_ONLY && storedCode == null) {
            throw new WrongPhoneNumberException("No code was found for the specified phone number. " +
                    "The code may have been sent to another number.");
        }

        AuthStrategy strategy = strategies.stream()
                .filter(s -> s.getAuthMethod() == user.getAuthMethod())
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("No strategy for: " + user.getAuthMethod()));

        boolean ok = strategy.authenticate(user, request);
        if (!ok) {
            throw new WrongCredentialsException("Authentication failed for method = " + user.getAuthMethod());
        }

        if (user.getAuthMethod() == AuthMethod.SMS_ONLY || user.getAuthMethod() == AuthMethod.PASSWORD_SMS) {
            codeStorage.removeCodeForPhone(request.getPhoneNumber());
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

    @Transactional
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

        if (patch.getEmail() != null) {
            user.setEmail(patch.getEmail());
        }

        if (patch.getBalance() != null) {
            user.setBalance(patch.getBalance());
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

    @Transactional
    public void changeAuthMethod(ChangeAuthMethodRequest dto) {
        User user = currentUserService.getCurrentUserOrThrow();

        user.setAuthMethod(dto.getNewMethod());
        if ((dto.getNewMethod() == AuthMethod.PASSWORD_ONLY
                || dto.getNewMethod() == AuthMethod.PASSWORD_SMS)
                && dto.getNewPassword() != null)
        {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        userRepository.save(user);
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
        }
    }
}