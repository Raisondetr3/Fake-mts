package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("verifyPasswordDelegate")
@RequiredArgsConstructor
public class VerifyPasswordDelegate implements JavaDelegate {
    private final PasswordEncoder encoder;
    @Override
    public void execute(DelegateExecution ex) {
        String plain = (String) ex.getVariable("plainPassword");
        String hash  = (String) ex.getVariable("passwordHash");
        if (!encoder.matches(plain, hash)) {
            throw new BpmnError("BAD_PASSWORD");
        }
    }
}
