package com.destructio.springbootproducer;

import java.time.ZonedDateTime;

public record Message(
        String title,
        String message,
        ZonedDateTime messageTime,
        Boolean isMessageGood,
        Long messageSum
) {
}
