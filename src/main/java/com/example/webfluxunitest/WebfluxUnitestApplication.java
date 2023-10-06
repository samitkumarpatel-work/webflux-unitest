package com.example.webfluxunitest;

import com.example.webfluxunitest.kafka.KafkaService;
import com.example.webfluxunitest.models.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebfluxUnitestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxUnitestApplication.class, args);
	}
}

@Component
@RequiredArgsConstructor
class Routers {

	private final KafkaService kafkaService;
	@Bean
	public RouterFunction routerFunction() {
		return RouterFunctions
				.route()
				.path("/kafka", builder -> builder
						.POST("/produce", this::kafkaProducer)
				)
				.path("/db", builder -> builder
						.GET("", request -> ServerResponse.noContent().build())
				)
				.build();
	}

	private Mono<ServerResponse> kafkaProducer(ServerRequest request) {
		return request
				.bodyToMono(Message.class)
				.log()
				.map(message -> message)
				.flatMap(kafkaService::produce)
				.flatMap(o -> ServerResponse.ok().bodyValue(o));
	}
}

