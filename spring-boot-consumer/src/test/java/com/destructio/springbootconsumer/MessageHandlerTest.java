package com.destructio.springbootconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static java.time.LocalDateTime.now;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class MessageHandlerTest {
    @Autowired
    private KafkaTemplate<String, Message> messageTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Container
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka-native")
            .asCompatibleSubstituteFor("apache/kafka"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Test
    void shouldHandleMessage() {
        //given
        Message message = new Message("test", "test", now(), true, 10L);

        // when
        messageTemplate.send("messages", "test-key-1", message);

        // then
        await()
                .pollInterval(Duration.ofSeconds(1))
                .atMost(5, SECONDS)
                .untilAsserted(() -> assertEquals(1, messageRepository.count()));

        assertTrue(messageRepository.findById("test-key-1").isPresent());
        assertEquals(message, messageRepository.findById("test-key-1").get());
    }
}