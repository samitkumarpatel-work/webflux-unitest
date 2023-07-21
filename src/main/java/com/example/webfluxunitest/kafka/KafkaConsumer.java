package com.example.webfluxunitest.kafka;

import com.example.webfluxunitest.db.MessageRepository;
import com.example.webfluxunitest.models.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final MessageRepository messageRepository;

    @KafkaListener(id = "fooGroup", topics = "topic1")
    public void listen(Message message) {
        log.info("Received: {}" , message);

        messageRepository
                .save(message)
                .doOnSuccess(m -> log.info("message saved successfully"))
                .doOnError(e -> log.error("{}", e.getMessage()))
                .subscribe();
    }
}
