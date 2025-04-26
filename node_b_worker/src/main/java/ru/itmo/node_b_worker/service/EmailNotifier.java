package ru.itmo.node_b_worker.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EmailNotifier {

//    @Resource(lookup = "java:/jboss/mail/Default")
//    private Session mailSession;
//
//    public void notifyAdmins(AdminMailMessage msg) {
//        try {
//            MimeMessage mime = new MimeMessage(mailSession);
//            mime.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
//            mime.setRecipient(MimeMessage.RecipientType.TO,
//                    new InternetAddress(msg.getEmail()));
//            mime.setSubject("Запрос на ADMIN-права");
//            mime.setText(
//                    "Пользователь " + msg.getFullName() +
//                            " (ID=" + msg.getUserId() + ") запрашивает права."
//            );
//            Transport.send(mime);
//        } catch (MessagingException e) {
//            throw new RuntimeException("Не удалось отправить e-mail", e);
//        }
//    }
}
