package ru.itmo.node_a_core.listener;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("initVariablesListener")
public class InitVariablesListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
//        execution.setVariable("userAuthenticated", "Yes");
        execution.setVariable("userAuthenticated", "No");

        execution.setVariable("needToChangeCategory", "Yes");
        execution.setVariable("needToChangePeriod", "Yes");
        execution.setVariable("thePeriod", "In the last week");

        execution.setVariable("needToChangeInfo", "Yes");
        execution.setVariable("needToAddPayMethod", "No");
        execution.setVariable("needToCutomizeSecurity", "No");
    }
}