package ru.itmo.node_a_core.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.CompleteAuthRequest;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.common.entity.User;
import ru.itmo.node_a_core.service.CodeStorage;

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
    public boolean authenticate(User user, CompleteAuthRequest authRequest) {
        boolean passOk = (authRequest.getPassword() != null)
                && passwordEncoder.matches(authRequest.getPassword(), user.getPassword());

        String storedCode = codeStorage.getCodeForPhone(user.getPhoneNumber());
        boolean smsOk = (storedCode != null) && storedCode.equals(authRequest.getSmsCode());

        return passOk && smsOk;
    }
}



