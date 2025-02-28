package ru.itmo.fake_mts.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.fake_mts.dto.AuthRequest;
import ru.itmo.fake_mts.entity.AuthMethod;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.service.CodeStorage;

@Component
@RequiredArgsConstructor
public class SmsOnlyAuthStrategy implements AuthStrategy {

    private final CodeStorage codeStorage;

    @Override
    public AuthMethod getAuthMethod() {
        return AuthMethod.SMS_ONLY;
    }

    @Override
    public boolean authenticate(User user, AuthRequest authRequest) {
        String storedCode = codeStorage.getCodeForPhone(user.getPhoneNumber());
        if (storedCode == null) {
            return false;
        }
        return storedCode.equals(authRequest.getSmsCode());
    }
}


