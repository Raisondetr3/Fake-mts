package ru.itmo.fake_mts.service;

import ru.itmo.fake_mts.dto.UserPatchRequest;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.entity.UserStatus;
import ru.itmo.fake_mts.exception.AuthenticationException;
import ru.itmo.fake_mts.exception.InvalidPhoneNumberException;
import ru.itmo.fake_mts.exception.UserNotFoundException;
import ru.itmo.fake_mts.repo.UserRepository;
import ru.itmo.fake_mts.security.JwtService;
import ru.itmo.fake_mts.dto.AuthRequest;
import ru.itmo.fake_mts.security.strategy.AuthStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SmsService smsService;
    private final CodeStorage codeStorage;
    private final List<AuthStrategy> strategies;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String startAuth(String phoneNumber) {
        validatePhoneNumber(phoneNumber);

        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if (user == null) {
            user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setAuthMethod(AuthMethod.SMS_ONLY);
            user.setStatus(UserStatus.ACTIVE);
            user.setCreatedAt(LocalDateTime.now());
            user = userRepository.save(user);
        }

        AuthMethod method = user.getAuthMethod();

        if (method == AuthMethod.SMS_ONLY || method == AuthMethod.PASSWORD_SMS) {
            String code = generateRandomCode();
            codeStorage.saveCodeForPhone(phoneNumber, code);
            smsService.sendSms(phoneNumber, code);
            return "OK: SMS code sent. Auth method = " + method;
        } else {
            return "OK: no SMS needed. Auth method = " + method;
        }
    }

    public String completeAuth(String phoneNumber, AuthRequest authRequest) {
        validatePhoneNumber(phoneNumber);

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User with phone " + phoneNumber + " not found"));

        AuthStrategy strategy = strategies.stream()
                .filter(s -> s.getAuthMethod() == user.getAuthMethod())
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("No strategy for: " + user.getAuthMethod()));

        boolean ok = strategy.authenticate(user, authRequest);
        if (!ok) {
            throw new AuthenticationException("Authentication failed for method = " + user.getAuthMethod());
        }

        if (user.getAuthMethod() == AuthMethod.SMS_ONLY || user.getAuthMethod() == AuthMethod.PASSWORD_SMS) {
            codeStorage.removeCodeForPhone(phoneNumber);
        }

        return jwtService.generateToken(user);
    }

    private String generateRandomCode() {
        return "1234";
    }

    public User patchUser(Long userId, UserPatchRequest patch) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        if (patch.getFullName() != null) {
            user.setFullName(patch.getFullName());
        }
        if (patch.getSnils() != null) {
            user.setSnils(patch.getSnils());
        }
        if (patch.getInn() != null) {
            user.setInn(patch.getInn());
        }
        if (patch.getReservePhone() != null) {
            user.setReservePhone(patch.getReservePhone());
        }
        if (patch.getEmailBackup() != null) {
            user.setEmailBackup(patch.getEmailBackup());
        }

        return userRepository.save(user);
    }

    public User changeAuthMethod(Long userId, AuthMethod newMethod, String rawPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));


        user.setAuthMethod(newMethod);
        if ((newMethod == AuthMethod.PASSWORD_ONLY || newMethod == AuthMethod.PASSWORD_SMS)
                && rawPassword != null) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        return userRepository.save(user);
    }

    private void validatePhoneNumber(String phoneNumber) {
//        if (!phoneNumber.matches("^[+]?\\d+$")) {
          if (!phoneNumber.matches("^\\d{10}$")) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + phoneNumber);
        }
    }
}