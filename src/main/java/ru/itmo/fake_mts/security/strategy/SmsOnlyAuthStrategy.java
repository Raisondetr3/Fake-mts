package ru.itmo.fake_mts.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.fake_mts.dto.CompleteAuthRequest;
import ru.itmo.fake_mts.entity.enums.AuthMethod;
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
    public boolean authenticate(User user, CompleteAuthRequest authRequest) {
        String storedCode = codeStorage.getCodeForPhone(user.getPhoneNumber());
        System.out.println("Stored code: " + storedCode);
        System.out.println("Received code: " + authRequest.getSmsCode());

        if (storedCode == null) {
            System.out.println("Authentication failed: no stored code found");
            return false;
        }

        boolean isValid = storedCode.equals(authRequest.getSmsCode());
        System.out.println("Authentication result: " + isValid);
        return isValid;
    }
}



