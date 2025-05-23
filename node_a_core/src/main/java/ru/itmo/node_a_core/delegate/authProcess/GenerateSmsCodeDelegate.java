package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.UserService;

@Component("generateSmsCodeDelegate")
@RequiredArgsConstructor
public class GenerateSmsCodeDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution ex) {
        String phone = (String) ex.getVariable("phoneNumber");
        String code = userService.generateAndSaveSmsCode(phone);
        ex.setVariable("smsCode", code);
    }
}

