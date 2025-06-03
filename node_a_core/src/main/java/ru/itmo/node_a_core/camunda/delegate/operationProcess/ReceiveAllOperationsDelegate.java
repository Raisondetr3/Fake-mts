package ru.itmo.node_a_core.camunda.delegate.operationProcess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.OperationPresentation;
import ru.itmo.node_a_core.service.OperationService;
import ru.itmo.node_a_core.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component("receiveAllOperationsDelegate")
@RequiredArgsConstructor
public class ReceiveAllOperationsDelegate implements JavaDelegate {
    private final OperationService operationService;

    @Override
    public void execute(DelegateExecution ex) {
        LocalDateTime periodStart = DateUtils.convertToLocalDateTimeViaInstant((Date) ex.getVariable("periodStart"));
        LocalDateTime periodEnd = DateUtils.convertToLocalDateTimeViaInstant((Date) ex.getVariable("periodEnd"));
        List<OperationPresentation> operationPresentations = operationService.getAllOperationsByPeriod(periodStart, periodEnd);
        ex.setVariable("operations", operationPresentations.toString());
    }
}
