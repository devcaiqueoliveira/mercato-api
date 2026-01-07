package com.devcaiqueoliveira.mercato.exception;

import java.time.Instant;

public record StandardError(
        Instant timeStamp,
        Integer status,
        String error,
        String message,
        String path
) {
}