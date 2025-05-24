package ru.itmo.node_a_core.delegate.adminProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.AdminRequestService;

@Component("approveAdminRequestStatusDelegate")
@RequiredArgsConstructor
public class ApproveAdminRequestStatusDelegate implements JavaDelegate {
    private final AdminRequestService adminRequestService;

    @Override
    public void execute(DelegateExecution ex) {
        adminRequestService.approveAdminRequest((Long) ex.getVariable("targetUserId"));
    }
}
