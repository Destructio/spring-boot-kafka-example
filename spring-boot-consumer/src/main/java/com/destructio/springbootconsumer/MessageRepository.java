package com.destructio.springbootconsumer;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MessageRepository {
    private final ConcurrentHashMap<String, Message> messagesMap;

    public MessageRepository() {
        this.messagesMap = new ConcurrentHashMap<>();
    }

    public void save(Message message) {
        messagesMap.put(message.title(), message);
    }

    public Optional<Message> findById(String id) {
        return Optional.ofNullable(messagesMap.get(id));
    }

    public int count() {
        return messagesMap.size();
    }
}
