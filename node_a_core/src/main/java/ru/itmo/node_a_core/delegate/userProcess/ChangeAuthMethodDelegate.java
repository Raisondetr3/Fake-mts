package ru.itmo.node_a_core.delegate.userProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.ChangeAuthMethodRequest;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.node_a_core.service.UserService;

@Component("changeAuthMethodDelegate")
@RequiredArgsConstructor
public class ChangeAuthMethodDelegate implements JavaDelegate {
    private UserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        AuthMethod newMethod = (AuthMethod) execution.getVariable("newMethod");
        String newPassword = (String) execution.getVariable("newPassword");

        ChangeAuthMethodRequest request = new ChangeAuthMethodRequest(newMethod, newPassword);

        userService.changeAuthMethod(request);
    }
}