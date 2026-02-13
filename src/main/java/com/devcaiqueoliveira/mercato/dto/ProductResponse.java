package com.devcaiqueoliveira.mercato.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String barCode,
        String sku,
        BigDecimal salePrice,
        BigDecimal stockQuantity,
        Boolean active,
        CategoryResponse category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
