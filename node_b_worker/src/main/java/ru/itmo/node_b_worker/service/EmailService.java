package ru.itmo.node_b_worker.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class EmailService {
    @SneakyThrows
    public void sendEmail(String emailTo, String messageText) {
//        System.out.print("EmailService: " + emailTo + " " + messageText);
        log.info("EmailService: {} {}", emailTo, messageText);
        if (emailTo == null) return;
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");//Enable tls session
        props.put("mail.smtp.auth", "true");//Enable authentication
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.yandex.ru");//Server's host
        props.put("mail.smtp.port", "465");//Server's port

        //TODO вынести отсюда
        //логин и пароль yandex mail пользователя
        String userLogin = "367149@edu.itmo.ru";

        String userPassword = "";

        Session session = Session.getDefaultInstance(props,
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
