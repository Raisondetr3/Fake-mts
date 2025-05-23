package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.UserService;

@Component("sendSmsDelegate")
@RequiredArgsConstructor
public class SendSmsDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution ex) {
        userService.publishSmsCode(
                (String) ex.getVariable("phoneNumber"),
                (String) ex.getVariable("smsCode")
        );
    }
}

