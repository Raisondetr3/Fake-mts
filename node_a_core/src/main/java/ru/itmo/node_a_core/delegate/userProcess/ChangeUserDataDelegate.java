package ru.itmo.node_a_core.delegate.userProcess;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.UserPatchRequest;
import ru.itmo.node_a_core.service.UserService;

@Component("changeUserDataDelegate")
@RequiredArgsConstructor
public class ChangeUserDataDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution ex) {
        UserPatchRequest userPatchRequest = new UserPatchRequest();
        userPatchRequest.setFullName((String) ex.getVariable("fullName"));
        userPatchRequest.setSnils((String) ex.getVariable("snils"));
        userPatchRequest.setInn((String) ex.getVariable("inn"));
        userPatchRequest.setEmail((String) ex.getVariable("email"));
        userPatchRequest.setBalance((Double) ex.getVariable("balance"));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        validator.validate(userPatchRequest);
        factory.close();

        userService.patchUser(userPatchRequest);
    }
}