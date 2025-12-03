package com.devcaiqueoliveira.mercatopdvsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "É obrigatório inserir um nome no produto")
        String name,

        String description,

        @NotBlank(message = "O código de barras é obrigatório")
        String barCode,

        String sku,

        @PositiveOrZero
        BigDecimal costPrice,

        @NotNull @Positive(message = "Preço de venda deve ser maior que zero.")
        BigDecimal salePrice,

        @NotNull @PositiveOrZero
        BigDecimal stockQuantity,

        @NotNull
        String unitOfMeasure,

        String ncmCode,

        Long categoryId,

        Boolean active
) {
}