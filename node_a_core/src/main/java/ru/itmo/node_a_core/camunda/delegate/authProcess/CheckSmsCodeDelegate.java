package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.CodeStorage;

import java.util.Objects;

@Component("checkSmsCodeDelegate")
@RequiredArgsConstructor
public class CheckSmsCodeDelegate implements JavaDelegate {
    private final CodeStorage codeStorage;
    @Override
    public void execute(DelegateExecution ex) {
        String phone = (String) ex.getVariable("phoneNumber");
        String entered = (String) ex.getVariable("enteredSmsCode");
        String stored  = codeStorage.getCodeForPhone(phone);
        if (!Objects.equals(entered, stored)) {
            throw new BpmnError("SMS_MISMATCH");
        }
    }
}

