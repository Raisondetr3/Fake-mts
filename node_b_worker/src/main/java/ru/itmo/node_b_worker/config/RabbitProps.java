package ru.itmo.node_b_worker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitProps {
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost = "/";
}
