package ru.itmo.node_a_core.delegate.operationProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.OperationPresentation;
import ru.itmo.node_a_core.service.OperationService;

import java.time.LocalDateTime;
import java.util.List;

@Component("receiveOperationsDelegate")
@RequiredArgsConstructor
public class ReceiveOperationsDelegate implements JavaDelegate {
    private final OperationService operationService;

    @Override
    public void execute(DelegateExecution ex) {
        LocalDateTime periodStart = (LocalDateTime) ex.getVariable("periodStart");
        LocalDateTime periodEnd = (LocalDateTime) ex.getVariable("periodEnd");
        List<OperationPresentation> operationPresentations = operationService.getAllOperationsByPeriod(periodStart, periodEnd);
        ex.setVariable("operations", operationPresentations);
    }
}
