package com.destructio.springbootproducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.time.ZonedDateTime.now;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class MessageProducerService {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private static final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        Message message = new Message(
                "Sample Message title",
                "Test " + random.nextInt(),
                now(),
                random.nextBoolean(),
                random.nextLong()
        );
        kafkaTemplate.send("messages", message)
                .thenAccept((result -> log.info("Sent message! {}", result.getRecordMetadata().toString())));
    }
}
