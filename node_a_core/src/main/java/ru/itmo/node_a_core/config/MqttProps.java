package ru.itmo.node_a_core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttProps {
    private String brokerUrl;
    private String clientId;
    private String smsTopic;
    private String adminTopic;
}

