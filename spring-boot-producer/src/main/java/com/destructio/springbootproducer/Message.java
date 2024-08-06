package com.destructio.springbootproducer;

import java.time.LocalDateTime;

public record Message(
        String title,
        String message,
        LocalDateTime messageTime,
        Boolean isMessageGood,
        Long messageSum
) {
}
