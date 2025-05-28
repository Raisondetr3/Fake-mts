package ru.itmo.node_a_core.camunda.delegate.tariffManagement;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.entity.User;
import ru.itmo.common.repo.UserRepository;

@Component("getBalanceDelegate")
@RequiredArgsConstructor
public class GetBalanceDelegate implements JavaDelegate {
    private final UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) {
        Long userId = ((Number) execution.getVariable("userId")).longValue();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BpmnError(
                        "USER_NOT_FOUND",
                        "Пользователь не найден: " + userId
                ));
        execution.setVariable("balance", user.getBalance());
    }
}