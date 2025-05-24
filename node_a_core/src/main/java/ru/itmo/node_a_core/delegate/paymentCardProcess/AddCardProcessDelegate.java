package ru.itmo.node_a_core.delegate.paymentCardProcess;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.AddCardRequest;
import ru.itmo.node_a_core.service.PaymentCardService;

@Component("addCardProcessDelegate")
@RequiredArgsConstructor
public class AddCardProcessDelegate implements JavaDelegate {
    private final PaymentCardService cardService;

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

        cardService.addCard(addCardRequest);
    }
}
