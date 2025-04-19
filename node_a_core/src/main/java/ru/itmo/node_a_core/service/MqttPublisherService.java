package ru.itmo.node_a_core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itmo.node_a_core.config.MqttProps;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class MqttPublisherService {

    private final MqttClient client;
    private final ObjectMapper mapper;
    @Qualifier("mqtt-ru.itmo.node_a_core.config.MqttProps")
    private final MqttProps props;

    public void publish(Object dto) {
        try {
            String payload = mapper.writeValueAsString(dto);
            client.publish(props.getTopic(),
                    payload.getBytes(StandardCharsets.UTF_8),
                    1,
                    false);
        } catch (MqttException | JsonProcessingException e) {
            throw new RuntimeException("MQTT publish failed", e);
        }
    }
}