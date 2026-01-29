package com.devcaiqueoliveira.mercato.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaleItemRequest (
        @NotNull(message = "O ID do produto é obrigatório.")
        Long productId,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero.")
        BigDecimal quantity
){
}
