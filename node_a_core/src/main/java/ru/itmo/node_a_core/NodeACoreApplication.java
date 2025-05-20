package ru.itmo.node_a_core;

import io.github.cdimascio.dotenv.Dotenv;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableProcessApplication
@SpringBootApplication(
        exclude = {RabbitAutoConfiguration.class},
        scanBasePackages = {
                "ru.itmo.node_a_core",
                "ru.itmo.common"
        }
)
@EnableJpaRepositories(basePackages = {
        "ru.itmo.common.repo"
})
@EntityScan(basePackages = {
        "ru.itmo.common.entity"
})
public class NodeACoreApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("../")
                .filename(".env")
                .load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        SpringApplication.run(NodeACoreApplication.class, args);
    }
}
