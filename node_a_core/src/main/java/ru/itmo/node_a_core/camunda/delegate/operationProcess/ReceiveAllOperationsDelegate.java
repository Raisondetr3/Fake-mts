package ru.itmo.node_a_core.camunda.delegate.operationProcess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.OperationPresentation;
import ru.itmo.common.entity.enums.OperationType;
import ru.itmo.node_a_core.service.OperationService;
import ru.itmo.node_a_core.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component("receiveAllOperationsDelegate")
@RequiredArgsConstructor
public class ReceiveAllOperationsDelegate implements JavaDelegate {
    private final OperationService operationService;
    private static final LocalDateTime defaultPeriodStart = LocalDateTime.now();
    private static final LocalDateTime defaultPeriodEnd = LocalDateTime.now().minusYears(1);

    @Override
    public void execute(DelegateExecution ex) {
        Object rawPeriodStart = ex.getVariable("periodStart");
        Object rawPeriodEnd = ex.getVariable("periodEnd");
        Object rawCategory = ex.getVariable("category");
        LocalDateTime periodStart = rawPeriodStart != null
                ? DateUtils.convertToLocalDateTimeViaInstant((Date) (rawPeriodStart))
                : defaultPeriodStart;
        LocalDateTime periodEnd = rawPeriodEnd != null
                ? DateUtils.convertToLocalDateTimeViaInstant((Date) rawPeriodEnd)
                : defaultPeriodEnd;
        String category = rawCategory != null
                ?(String) rawCategory
                : "";
        List<OperationPresentation> operationPresentations;
        if (Arrays.stream(OperationType.values()).anyMatch(value -> value.name().equals(category))) {
            operationPresentations = operationService.getAllOperationsByPeriodAndType(periodStart, periodEnd, OperationType.valueOf(category));
        }
        else {
            operationPresentations = operationService.getAllOperationsByPeriod(periodStart, periodEnd);
        }
        ex.setVariable("operations", operationPresentations.toString());
    }
}
