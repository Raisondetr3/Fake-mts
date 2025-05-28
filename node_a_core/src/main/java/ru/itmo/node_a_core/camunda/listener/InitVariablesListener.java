package ru.itmo.node_a_core.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("initVariablesListener")
public class InitVariablesListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        execution.setVariable("userAuthenticated", "No");

        execution.setVariable("needToChangeCategory", "Yes");
        execution.setVariable("needToChangePeriod", "Yes");
        execution.setVariable("thePeriod", "In the last week");

        execution.setVariable("needToChangeInfo", "Yes");
        execution.setVariable("needToAddPayMethod", "No");
        execution.setVariable("needToCutomizeSecurity", "No");

        execution.setVariable("isThereAnAdmin", "Yes");
        execution.setVariable("userIsAdmin", "No");
    }
}