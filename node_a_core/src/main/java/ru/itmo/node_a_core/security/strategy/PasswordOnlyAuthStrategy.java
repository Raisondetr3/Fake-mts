package ru.itmo.node_a_core.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.CompleteAuthRequest;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.common.entity.User;

@Component
@RequiredArgsConstructor
public class PasswordOnlyAuthStrategy implements AuthStrategy {

    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthMethod getAuthMethod() {
        return AuthMethod.PASSWORD_ONLY;
    }

    @Override
    public boolean authenticate(User user, CompleteAuthRequest authRequest) {
        if (authRequest.getPassword() == null) {
            return false;
        }
        return passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
    }
}

