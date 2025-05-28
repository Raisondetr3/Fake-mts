package ru.itmo.node_a_core.camunda.delegate.tariffManagement;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.exception.NotEnoughMoneyException;
import ru.itmo.common.exception.TariffNotFoundException;
import ru.itmo.node_a_core.service.TariffService;

@Component("monthlyChargeDelegate")
@RequiredArgsConstructor
public class MonthlyChargeDelegate implements JavaDelegate {
    private final TariffService tariffService;

    @Override
    public void execute(DelegateExecution execution) {
        Long userId   = ((Number) execution.getVariable("userId")).longValue();
        Long tariffId = ((Number) execution.getVariable("tariffId")).longValue();
        try {
            tariffService.chargeMonthly(userId, tariffId);
        }
        catch (TariffNotFoundException ex) {
            throw new BpmnError("TARIFF_NOT_FOUND", ex.getMessage());
        }
        catch (NotEnoughMoneyException ex) {
            throw new BpmnError("NOT_ENOUGH_MONEY", ex.getMessage());
        }
    }
}
