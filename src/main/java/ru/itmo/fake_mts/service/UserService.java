package ru.itmo.fake_mts.service;

import ru.itmo.fake_mts.dto.UserPatchRequest;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.entity.UserStatus;
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
    private final List<AuthStrategy> strategies;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User register(String phoneNumber, String email, String rawPassword) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setAuthMethod(AuthMethod.SMS_ONLY);
        if (rawPassword != null) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public String authenticate(String phoneNumber, AuthRequest authRequest) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AuthStrategy strategy = strategies.stream()
                .filter(s -> s.getAuthMethod() == user.getAuthMethod())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No strategy found for: " + user.getAuthMethod()));

        boolean ok = strategy.authenticate(user, authRequest);
        if (!ok) {
            throw new RuntimeException("Authentication failed");
        }
        return jwtService.generateToken(user);
    }

    public User patchUser(Long userId, UserPatchRequest patch) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAuthMethod(newMethod);
        if ((newMethod == AuthMethod.PASSWORD_ONLY || newMethod == AuthMethod.PASSWORD_SMS)
                && rawPassword != null) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        return userRepository.save(user);
    }
}
