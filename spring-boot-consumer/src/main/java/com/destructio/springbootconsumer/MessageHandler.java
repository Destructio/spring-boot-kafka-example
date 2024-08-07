package com.destructio.springbootconsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler {
    private final MessageRepository messageRepository;

    @KafkaListener(topics = "messages")
    public void handleMessage(@Payload Message message) {
        log.info("Received a new message! - {}", message.toString());
        messageRepository.save(message);
    }
}
