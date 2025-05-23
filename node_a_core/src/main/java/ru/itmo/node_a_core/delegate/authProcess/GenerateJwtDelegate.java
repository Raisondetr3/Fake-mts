package ru.itmo.node_a_core.delegate.authProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.entity.User;
import ru.itmo.common.repo.UserRepository;
import ru.itmo.node_a_core.security.JwtService;

@Component("generateJwtDelegate")
@RequiredArgsConstructor
public class GenerateJwtDelegate implements JavaDelegate {
    private final JwtService jwtService;
    private final UserRepository userRepo;
    @Override
    public void execute(DelegateExecution ex) {
        String phone = (String) ex.getVariable("phoneNumber");
        User user    = userRepo.findByPhoneNumber(phone).get();
        String token = jwtService.generateToken(user);
        ex.setVariable("jwtToken", token);
    }
}

