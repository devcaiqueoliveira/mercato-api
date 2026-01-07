package com.devcaiqueoliveira.mercato.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        Boolean active
) {
}
