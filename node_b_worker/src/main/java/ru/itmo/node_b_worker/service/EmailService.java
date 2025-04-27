package ru.itmo.node_b_worker.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class EmailService {
    @Value("${EMAIL_USER_LOGIN}")
    private String userLogin;

    @Value("${EMAIL_USER_PASSWORD}")
    private String userPassword;

    @SneakyThrows
    public void sendEmail(String emailTo, String messageText) {
        log.info("Send email to {} with text {}", emailTo, messageText);
        if (emailTo == null) return;
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");//Enable tls session
        props.put("mail.smtp.auth", "true");//Enable authentication
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.yandex.ru");//Server's host
        props.put("mail.smtp.port", "465");//Server's port

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userLogin, userPassword);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userLogin));
        message.setSubject("test");
        message.setText(messageText);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        message.setSentDate(new Date());

        Transport.send(message);
    }

    public static void main(String[] args) {
        EmailService emailService = new EmailService();

        emailService.sendEmail("367149@edu.itmo.ru", "test");
    }
}
