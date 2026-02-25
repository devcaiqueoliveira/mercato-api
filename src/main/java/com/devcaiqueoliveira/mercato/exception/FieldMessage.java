package com.devcaiqueoliveira.mercato.exception;

public record FieldMessage(
        String fieldName,
        String message
) {
}
