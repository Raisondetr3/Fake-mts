package ru.itmo.node_b_worker.config;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJms
public class RabbitJmsConfig {

    @Bean
    public ConnectionFactory rabbitConnectionFactory(RabbitProps p) {
        RMQConnectionFactory cf = new RMQConnectionFactory();
        cf.setHost(p.getHost());
        cf.setPort(p.getPort());
        cf.setUsername(p.getUsername());
        cf.setPassword(p.getPassword());
        cf.setVirtualHost("/");
        return cf;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory cf, PlatformTransactionManager tx) {
        DefaultJmsListenerContainerFactory f = new DefaultJmsListenerContainerFactory();
        f.setConnectionFactory(cf);
        f.setTransactionManager(tx);
        f.setSessionTransacted(true);
        return f;
    }
}
