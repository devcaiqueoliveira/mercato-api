package com.devcaiqueoliveira.mercato.exception;

import java.time.Instant;
import java.util.List;

public record ValidationError(
        Instant timeStamp,
        Integer status,
        String error,
        String message,
        String path,
        List<FieldMessage> erros
        ) {
}
