package com.devcaiqueoliveira.mercatopdvsystem.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "O nome da categoria é obrigatório")
        String name,

        String description

) {
}
