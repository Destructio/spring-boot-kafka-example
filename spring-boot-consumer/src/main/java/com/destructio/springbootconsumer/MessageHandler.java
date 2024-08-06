package com.destructio.springbootconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageHandler {

    @KafkaListener(topics = "messages")
    private void printMessage(Message message) {
        log.info("Received a new message! - {}", message.toString());
    }
}
