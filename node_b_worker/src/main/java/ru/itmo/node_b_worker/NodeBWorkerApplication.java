package ru.itmo.node_b_worker;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.itmo.node_b_worker.service.EmailService;

@Slf4j
@SpringBootApplication(
		exclude = {RabbitAutoConfiguration.class},
		scanBasePackages = {
				"ru.itmo.node_b_worker",
				"ru.itmo.common"
		}
)
@EnableJpaRepositories(basePackages = {
		"ru.itmo.common.repo"
})
@EntityScan(basePackages = {
		"ru.itmo.common.entity"
})
public class NodeBWorkerApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
//				.directory("../")
				.filename(".env")
				.load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		SpringApplication.run(NodeBWorkerApplication.class, args);
	}

	@Bean
	public ApplicationRunner testMailer(EmailService emailService) {
		return args -> {
			emailService.sendEmail("Aakuma1337@gmail.com", "test+");
			log.info("SSendm");
		};
	}
}