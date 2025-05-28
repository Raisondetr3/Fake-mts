package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.repo.UserRepository;

@Component("fetchPasswordHashDelegate")
@RequiredArgsConstructor
public class FetchPasswordHashDelegate implements JavaDelegate {
    private final UserRepository userRepo;
    @Override
    public void execute(DelegateExecution ex) {
        String phone = (String) ex.getVariable("phoneNumber");
        String hash  = userRepo.findByPhoneNumber(phone)
                .orElseThrow(() -> new BpmnError("USER_NOT_FOUND"))
                .getPassword();
        ex.setVariable("passwordHash", hash);
    }
}
