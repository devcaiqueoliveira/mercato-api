package com.devcaiqueoliveira.mercatopdvsystem.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        Boolean active
) {
}
