package ru.itmo.node_a_core.camunda.delegate.userProcess;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.UserPatchRequest;
import ru.itmo.common.dto.UserResponse;
import ru.itmo.common.entity.User;
import ru.itmo.common.repo.UserRepository;
import ru.itmo.node_a_core.service.UserService;

import java.math.BigDecimal;

@Component("changeUserDataDelegate")
@RequiredArgsConstructor
public class ChangeUserDataDelegate implements JavaDelegate {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void execute(DelegateExecution ex) {
        UserPatchRequest userPatchRequest = new UserPatchRequest();
        if (ex.getVariable("fullname") != null && !ex.getVariable("fullname").equals("")) {
            userPatchRequest.setFullName((String) ex.getVariable("fullname"));
        }
        if (ex.getVariable("snils") != null && !ex.getVariable("snils").equals("")) {
            userPatchRequest.setSnils((String) ex.getVariable("snils"));
        }
        if (ex.getVariable("inn") != null && !ex.getVariable("inn").equals("")) {
            userPatchRequest.setInn((String) ex.getVariable("inn"));
        }
        if (ex.getVariable("email") != null && !ex.getVariable("email").equals("")) {
            userPatchRequest.setEmail((String) ex.getVariable("email"));
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        validator.validate(userPatchRequest);
        factory.close();

        Long targetUserId = (Long) ex.getVariable("targetUserId");
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new BpmnError(
                        "USER_NOT_FOUND",
                        "Пользователь не найден: " + targetUserId
                ));

        UserResponse userResponse = userService.patchUser(userPatchRequest, user);
        ex.setVariable("userResponse", userResponse.toString());
    }
}