package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.node_a_core.service.UserService;

@Component("determineAuthMethodDelegate")
@RequiredArgsConstructor
public class DetermineAuthMethodDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution ex) {
        String phone = (String) ex.getVariable("phoneNumber");
        AuthMethod method = userService.determineAuthMethod(phone);
        ex.setVariable("authMethod", method.name());
    }
}
