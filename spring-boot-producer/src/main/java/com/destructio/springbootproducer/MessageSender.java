package com.destructio.springbootproducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class MessageSender {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private static final Random random = new Random();

    public void sendMessage(Message message) {
        kafkaTemplate.send("messages", message)
                .thenAccept((result -> log.info("Sent message! {}", result.getRecordMetadata().toString())));
    }

    @Scheduled(fixedRate = 5000)
    public void sendGeneratedMessage() {
        Message message = generateRandomMessage();
        sendMessage(message);
    }

    private static Message generateRandomMessage() {
        return new Message(
                "Sample Message" + random.nextInt(900),
                "Test " + random.nextInt(900),
                now(),
                random.nextBoolean(),
                random.nextLong(10000)
        );
    }
}
