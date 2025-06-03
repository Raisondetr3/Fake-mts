package ru.itmo.node_b_worker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${EMAIL_USER_LOGIN}")
    private String userLogin;

    public void sendEmail(String to, String subj, String text) {
        if (to == null) {
            log.warn("Адрес получателя равен null, выходим из sendEmail.");
            return;
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(userLogin);
        msg.setTo(to);
        msg.setSubject(subj);
        msg.setText(text);
        mailSender.send(msg);

//        try {
//            log.info("Попытка отправить e-mail на адрес {}", to);
//            mailSender.send(msg);
//            log.info("Simple e-mail успешно отправлен на {}", to);
//        } catch (Exception e) {
//            log.error("Ошибка при отправке письма на {}: {}", to, e.toString(), e);
//        }
    }
}
