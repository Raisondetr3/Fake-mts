package ru.itmo.fake_mts.security.strategy;

import org.springframework.stereotype.Component;
import ru.itmo.fake_mts.dto.AuthRequest;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;

/**
 * 2) SMS_ONLY
 */
@Component
public class SmsOnlyAuthStrategy implements AuthStrategy {

    @Override
    public AuthMethod getAuthMethod() {
        return AuthMethod.SMS_ONLY;
    }

    @Override
    public boolean authenticate(User user, AuthRequest authRequest) {
        return "4815".equals(authRequest.getSmsCode());
    }
}

