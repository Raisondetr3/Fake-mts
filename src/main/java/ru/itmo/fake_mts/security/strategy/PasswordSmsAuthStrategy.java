package ru.itmo.fake_mts.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itmo.fake_mts.dto.AuthRequest;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.service.CodeStorage;

@Component
@RequiredArgsConstructor
public class PasswordSmsAuthStrategy implements AuthStrategy {

    private final PasswordEncoder passwordEncoder;
    private final CodeStorage codeStorage;

    @Override
    public AuthMethod getAuthMethod() {
        return AuthMethod.PASSWORD_SMS;
    }

    @Override
    public boolean authenticate(User user, AuthRequest authRequest) {
        boolean passOk = (authRequest.getPassword() != null)
                && passwordEncoder.matches(authRequest.getPassword(), user.getPassword());

        String storedCode = codeStorage.getCodeForPhone(user.getPhoneNumber());
        boolean smsOk = (storedCode != null) && storedCode.equals(authRequest.getSmsCode());

        return passOk && smsOk;
    }
}


