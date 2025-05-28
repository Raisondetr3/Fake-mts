package ru.itmo.node_a_core.camunda.delegate.adminProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.AdminRequestService;

@Component("approveAdminRequestStatusDelegate")
@RequiredArgsConstructor
public class ApproveAdminRequestStatusDelegate implements JavaDelegate {
    private final AdminRequestService service;

    @Override
    public void execute(DelegateExecution ex) {
        Object raw = ex.getVariable("targetUserId");
        Long id = raw != null
                ? ((Number) raw).longValue()
                : 1L;
        service.approve(id);
    }
}
