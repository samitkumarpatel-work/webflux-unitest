package com.example.webfluxunitest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class WebfluxUnitestApplicationTests {

	@Container
	@ServiceConnection
	final static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres"));

	@Container
	@ServiceConnection
	final static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"))
			.waitingFor(Wait.forListeningPort());

	@Test
	void contextLoads() {
	}

}
