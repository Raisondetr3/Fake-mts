package ru.itmo.node_b_worker.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.SmsMessage;
import ru.itmo.node_b_worker.service.SmsService;


@Component
@RequiredArgsConstructor
public class SmsListener {
    private final SmsService smsService;

    @RabbitListener(
            queues = "sms.queue",
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void onSmsMessage(SmsMessage msg) {
        smsService.sendSms(msg.getPhoneNumber(), msg.getSmsCode());
    }
}

