package ru.itmo.node_b_worker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.node_b_worker.eis.EmailConnection;
import ru.itmo.node_b_worker.eis.EmailConnectionFactory;
import

@Configuration
public class EisConfig {
    @Bean("eis/EmailCF")
    public EmailConnectionFactory emailConnectionFactory() {
        return new EmailConnectionFactory() {
            @Override
            public EmailConnection getConnection() {
                // заглушка, нужен JCA‑адаптер
                return new EmailConnection() {
                    @Override
                    public void sendMail(String to, String subject, String body) {
                        System.out.printf("[EIS‑stubbing] sendMail to=%s subj=%s%n", to, subject);
                    }

                    @Override
                    public void close() {
                    }
                };
            }
        };
    }
}