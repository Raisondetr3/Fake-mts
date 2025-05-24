package ru.itmo.node_a_core.delegate.adminProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.node_a_core.service.AdminRequestService;

@Component("rejectAdminRequestStatusDelegate")
@RequiredArgsConstructor
public class RejectAdminRequestStatusDelegate implements JavaDelegate {
    private final AdminRequestService adminRequestService;

    @Override
    public void execute(DelegateExecution ex) {
        adminRequestService.rejectAdminRequest((Long) ex.getVariable("targetUserId"));
    }
}