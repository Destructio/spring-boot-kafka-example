package com.destructio.springbootconsumer;

import java.time.LocalDateTime;

public record Message(
        String title,
        String message,
        LocalDateTime messageTime,
        Boolean isMessageGood,
        Long messageSum
) {
}
