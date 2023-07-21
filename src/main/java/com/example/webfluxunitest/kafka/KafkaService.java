package com.example.webfluxunitest.kafka;

import com.example.webfluxunitest.models.Message;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Value("${spring.kafka.producer.topicName:topic1}")
    private String topicName;

    public Mono<String> produce(Message message) {
        return Mono.fromCallable(() -> {
            return kafkaTemplate
                    .send(new ProducerRecord<>(topicName, message))
                    .get()
                    .getProducerRecord()
                    .partition();
        })
                .flatMap(integer -> Mono.just("Partition %s".formatted(integer)))
                .onErrorResume(e -> Mono.error(e));

    }

}
