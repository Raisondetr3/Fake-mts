package ru.itmo.node_b_worker.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.SmsMessage;
import ru.itmo.node_b_worker.service.SmsService;

@Component
@RequiredArgsConstructor
public class SmsListener {

    private final SmsService smsService;

    @JmsListener(
            destination = "sms.queue",
            containerFactory = "jmsListenerContainerFactory"
    )
    public void onSmsMessage(SmsMessage msg) {
        smsService.sendSms(msg.getPhoneNumber(), msg.getSmsCode());
    }
}
