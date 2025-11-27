package com.devcaiqueoliveira.mercatopdvsystem.controller.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        Boolean active
) {
}
