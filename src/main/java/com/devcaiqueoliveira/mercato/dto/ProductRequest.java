package com.devcaiqueoliveira.mercato.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record ProductRequest(

        @NotBlank(message = "É obrigatório inserir um nome no produto")
        String name,

        @NotBlank(message = "O código de barras é obrigatório")
        String barCode,

        String sku,

        @NotNull(message = "O preço de venda é obrigatório.") @Positive(message = "Preço de venda deve ser maior que zero.")
        BigDecimal salePrice,

        @NotNull @PositiveOrZero
        BigDecimal stockQuantity,

        @NotNull
        String unitOfMeasure,

        @NotNull(message = "Selecione uma categoria para o produto.")
        Long categoryId,

        Boolean active
) {
}