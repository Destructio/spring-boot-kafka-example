package com.destructio.springbootconsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler {
    private final MessageRepository messageRepository;

    @KafkaListener(topics = "messages")
    private void printMessage(@Header(RECEIVED_KEY) String key, @Payload Message message) {
        log.info("Received a new message! - {}", message.toString());
        messageRepository.save(key, message);
    }
}
