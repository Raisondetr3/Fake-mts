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
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static ru.itmo.common.entity.enums.AuthMethod.SMS_ONLY;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final CodeStorage codeStorage;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MqttPublisherService publisher;

    @Transactional
    public StartAuthResponse startAuth(String phone) {
        AuthMethod method = determineAuthMethod(phone);

        if (method == SMS_ONLY || method == AuthMethod.PASSWORD_SMS) {
            String code = generateAndSaveSmsCode(phone);
            publishSmsCode(phone, code);
        }

        return StartAuthResponse.builder()
                .message("OK: " + ((method == SMS_ONLY || method == AuthMethod.PASSWORD_SMS)
                        ? "SMS code sent." : "no SMS needed."))
                .authMethod(method.name())
                .build();
    }

    public String generateAndSaveSmsCode(String phone) {
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
        codeStorage.saveCodeForPhone(phone, code);
        return code;
    }

    @Transactional
    public AuthMethod determineAuthMethod(String phone) {
        User user = userRepository.findByPhoneNumber(phone).orElse(null);
        if (user == null) {
            user = User.builder()
                    .phoneNumber(phone)
                    .authMethod(SMS_ONLY)
                    .status(UserStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
            user = userRepository.save(user);
        }

        return user.getAuthMethod();
    }

    @Transactional
    public AuthCompleteResponse completeAuth(CompleteAuthRequest request) {
        String phone = request.getPhoneNumber();
        validatePhoneNumber(phone);

        User user = fetchUserOrThrow(phone);

        checkSmsCodeIfNeeded(user, request.getSmsCode());

        String hash = fetchPasswordHashIfNeeded(user);
        verifyPasswordIfNeeded(request.getPassword(), hash);

        removeCodeAfterAuth(user);

        String token = generateJwtToken(user);
        return AuthCompleteResponse.builder()
                .token(token)
                .build();
    }
    public User fetchUserOrThrow(String phone) {
        return userRepository.findByPhoneNumber(phone)
                .orElseThrow(() ->
                        new UserNotFoundException("User with phone " + phone + " not found"));
    }

    public void checkSmsCodeIfNeeded(User user, String enteredCode) {
        if (user.getAuthMethod() == AuthMethod.SMS_ONLY ||
                user.getAuthMethod() == AuthMethod.PASSWORD_SMS) {

            String stored = codeStorage.getCodeForPhone(user.getPhoneNumber());
            if (stored == null || !stored.equals(enteredCode)) {
                throw new WrongCredentialsException("Invalid SMS code");
            }
        }
    }

    public String fetchPasswordHashIfNeeded(User user) {
        if (user.getAuthMethod() == AuthMethod.PASSWORD_ONLY ||
                user.getAuthMethod() == AuthMethod.PASSWORD_SMS) {
            String hash = user.getPassword();
            if (hash == null) {
                throw new AuthenticationException("Password not set for user");
            }
            return hash;
        }
        return null;
    }

    public void verifyPasswordIfNeeded(String plainPassword, String hash) {
        if (hash != null && !passwordEncoder.matches(plainPassword, hash)) {
            throw new WrongCredentialsException("Invalid password");
        }
    }

    public void removeCodeAfterAuth(User user) {
        if (user.getAuthMethod() == AuthMethod.SMS_ONLY ||
                user.getAuthMethod() == AuthMethod.PASSWORD_SMS) {
            codeStorage.removeCodeForPhone(user.getPhoneNumber());
        }
    }

    public String generateJwtToken(User user) {
        return jwtService.generateToken(user);
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

    public void publishSmsCode(String phone, String code) {
        publisher.publish(new SmsMessage(phone, code), "sms.queue");
    }
}