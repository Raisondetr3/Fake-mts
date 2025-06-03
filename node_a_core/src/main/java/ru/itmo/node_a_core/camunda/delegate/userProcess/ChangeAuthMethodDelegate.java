package ru.itmo.node_a_core.camunda.delegate.userProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.ChangeAuthMethodRequest;
import ru.itmo.common.entity.User;
import ru.itmo.common.entity.enums.AuthMethod;
import ru.itmo.common.repo.UserRepository;
import ru.itmo.node_a_core.service.UserService;

@Component("changeAuthMethodDelegate")
@RequiredArgsConstructor
public class ChangeAuthMethodDelegate implements JavaDelegate {
    private UserService userService;
    private final UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        AuthMethod newMethod = (AuthMethod) execution.getVariable("newMethod");
        String newPassword = (String) execution.getVariable("newPassword");

        ChangeAuthMethodRequest request = new ChangeAuthMethodRequest(newMethod, newPassword);

        Long targetUserId = (Long) execution.getVariable("targetUserId");
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new BpmnError(
                        "USER_NOT_FOUND",
                        "Пользователь не найден: " + targetUserId
                ));

        userService.changeAuthMethod(request, user);
    }
}