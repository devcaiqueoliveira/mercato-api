package com.devcaiqueoliveira.mercato.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaleItemResponse (
        Long productId,
        String productName,
        BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}
