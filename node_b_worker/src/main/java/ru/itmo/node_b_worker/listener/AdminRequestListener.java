package ru.itmo.node_b_worker.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.itmo.common.dto.AdminMailMessage;
import ru.itmo.node_b_worker.service.EmailNotifier;

@Component
@RequiredArgsConstructor
class AdminRequestListener {

    private final EmailNotifier emailNotifier;

    @JmsListener(destination = "admin.queue", containerFactory = "jmsListenerContainerFactory")
    public void onAdmin(AdminMailMessage dto) {
        emailNotifier.notifyAdmins(dto);
    }
}