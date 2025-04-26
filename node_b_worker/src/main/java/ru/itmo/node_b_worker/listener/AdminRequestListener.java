package ru.itmo.node_b_worker.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.AdminRequestMessage;
import ru.itmo.node_b_worker.service.EmailService;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminRequestListener {
    private final EmailService emailService;

    @RabbitListener(
            queues = "admin.queue",
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void onAdminRequestMessage(AdminRequestMessage message) {
//        System.out.print("AdminRequestListener loog");
        log.info("AdminRequestListener loog");
        emailService.sendEmail(message.getEmail(), message.getMessage());
    }
}
