package ru.itmo.node_b_worker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itmo.common.dto.AdminMailMessage;
import ru.itmo.node_b_worker.eis.EmailConnection;
import ru.itmo.node_b_worker.eis.EmailConnectionFactory;

@Service
@RequiredArgsConstructor
public class EmailNotifier {

    @Qualifier("eis/EmailCF")
    private final EmailConnectionFactory emailCF;

    public void notifyAdmins(AdminMailMessage msg) {
        try (EmailConnection conn = emailCF.getConnection()) {
            conn.sendMail(
                    msg.getEmail(),
                    "Запрос на ADMIN‑права",
                    "Пользователь " + msg.getFullName() +
                            " (ID=" + msg.getUserId() + ") запрашивает права."
            );
        } catch (Exception e) {
            throw new RuntimeException("Не удалось отправить admin‑email", e);
        }
    }
}