package ru.itmo.node_a_core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itmo.node_a_core.config.MqttProps;

import java.nio.charset.StandardCharsets;

@Service
public class MqttPublisherService {

    private final MqttClient client;
    private final ObjectMapper mapper;
    private final MqttProps props;

    @Autowired
    public MqttPublisherService(MqttClient client, ObjectMapper mapper, @Qualifier("mqttProps") MqttProps props) {
        this.client = client;
        this.mapper = mapper;
        this.props = props;
    }

    public void publish(Object dto, String topic) {
        try {
            String payload = mapper.writeValueAsString(dto);
            client.publish(topic,
                    payload.getBytes(StandardCharsets.UTF_8),
                    1,
                    false);
        } catch (MqttException | JsonProcessingException e) {
            throw new RuntimeException("MQTT publish failed", e);
        }
    }
}
