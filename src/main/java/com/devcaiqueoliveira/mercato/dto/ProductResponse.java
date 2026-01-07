package com.devcaiqueoliveira.mercato.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String barCode,
        String sku,
        BigDecimal salePrice,
        BigDecimal stockQuantity,
        String unitOfMeasure,
        Boolean active,
        CategoryResponse category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
