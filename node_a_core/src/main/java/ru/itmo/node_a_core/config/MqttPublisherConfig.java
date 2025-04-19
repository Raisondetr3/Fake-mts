package ru.itmo.node_a_core.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties(MqttProps.class)
public class MqttPublisherConfig {

    @Bean(destroyMethod = "disconnect")
    public MqttClient mqttClient(@Qualifier("mqtt-ru.itmo.node_a_core.config.MqttProps") MqttProps props)
            throws MqttException {
        MqttClient client = new MqttClient(props.getBrokerUrl(), props.getClientId(), null);
        MqttConnectOptions opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(true);
        client.connect(opts);
        return client;
    }
}
