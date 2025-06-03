package ru.itmo.node_a_core.camunda.delegate.paymentCardProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.AddCardRequest;
import ru.itmo.common.entity.User;
import ru.itmo.common.repo.UserRepository;
import ru.itmo.node_a_core.service.PaymentCardService;

@Component("addCardProcessDelegate")
@RequiredArgsConstructor
public class AddCardProcessDelegate implements JavaDelegate {
    private final PaymentCardService cardService;
    private final UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String pan = (String) execution.getVariable("pan");
        Integer expiryMonth = (Integer) execution.getVariable("expiryMonth");
        Integer expiryYear = (Integer) execution.getVariable("expiryYear");
        String cardHolderName = (String) execution.getVariable("cardHolderName");

        AddCardRequest addCardRequest = new AddCardRequest();
        addCardRequest.setPan(pan);
        addCardRequest.setExpiryMonth(expiryMonth);
        addCardRequest.setExpiryYear(expiryYear);
        addCardRequest.setCardHolderName(cardHolderName);

        Long targetUserId = (Long) execution.getVariable("targetUserId");
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new BpmnError(
                        "USER_NOT_FOUND",
                        "Пользователь не найден: " + targetUserId
                ));

        cardService.addCard(addCardRequest, user);
    }
}
