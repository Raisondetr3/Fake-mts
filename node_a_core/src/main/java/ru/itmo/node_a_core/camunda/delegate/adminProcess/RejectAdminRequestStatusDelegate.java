package ru.itmo.node_a_core.camunda.delegate.adminProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.AdminRequestService;

@Component("rejectAdminDelegate")
@RequiredArgsConstructor
public class RejectAdminRequestStatusDelegate implements JavaDelegate {
    private final AdminRequestService service;
    @Override public void execute(DelegateExecution ex) {
        Object var = ex.getVariable("targetUserId");
        if (var == null) {
            throw new IllegalStateException("Не найдено process-variable 'targetUserId'! Проверьте, что она устанавливается до этой точки.");
        }
        Long id = ((Number) var).longValue();
        service.reject(id);
    }
}