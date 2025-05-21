package ru.itmo.node_a_core.delegate;

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
    public void execute(DelegateExecution execution) {
        String phone = (String) execution.getVariable("phoneNumber");
        String code  = (String) execution.getVariable("smsCode");

        userService.publishSmsCode(phone, code);
    }
}

