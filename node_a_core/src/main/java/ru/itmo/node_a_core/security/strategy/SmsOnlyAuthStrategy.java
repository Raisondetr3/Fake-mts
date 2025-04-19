package ru.itmo.node_a_core.security.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.CompleteAuthRequest;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.common.entity.User;
import ru.itmo.node_a_core.service.CodeStorage;

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



