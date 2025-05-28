package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.CodeStorage;

@Component("clearSmsCodeDelegate")
@RequiredArgsConstructor
public class ClearSmsCodeDelegate implements JavaDelegate {

    private final CodeStorage codeStorage;

    @Override
    public void execute(DelegateExecution ex) {
        String phone = (String) ex.getVariable("phoneNumber");
        codeStorage.removeCodeForPhone(phone);
    }
}

