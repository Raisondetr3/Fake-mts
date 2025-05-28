package ru.itmo.node_a_core.camunda.delegate.adminProcess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("groupCheckDelegate")
@RequiredArgsConstructor
public class GroupCheckDelegate implements JavaDelegate {
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) {
        String userId = (String) execution.getVariable("initiator");
        boolean isAdmin = identityService.createGroupQuery()
                .groupMember(userId)
                .groupId("admin")
                .singleResult() != null;
        execution.setVariable("isAdmin", isAdmin);
        log.info("initiator = {}, isAdmin = {}", userId, isAdmin);
    }
}
