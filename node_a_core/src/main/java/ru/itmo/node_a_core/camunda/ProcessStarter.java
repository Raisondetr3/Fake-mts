package ru.itmo.node_a_core.camunda;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProcessStarter {
    private final IdentityService identityService;
    private final RuntimeService  runtimeService;

    @Transactional
    public ProcessInstance start(String processId) {
        Authentication previousAuth = identityService.getCurrentAuthentication();

        try {
            identityService.clearAuthentication();

            return runtimeService.startProcessInstanceByKey(
                    processId
            );

        } finally {
            if (previousAuth != null) {
                identityService.setAuthentication(
                        previousAuth.getUserId(),
                        previousAuth.getGroupIds(),
                        previousAuth.getTenantIds()
                );
            }
        }
    }
}
