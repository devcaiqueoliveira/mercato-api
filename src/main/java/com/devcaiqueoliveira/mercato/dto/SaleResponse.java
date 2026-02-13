package com.devcaiqueoliveira.mercato.dto;

import com.devcaiqueoliveira.mercato.enums.SaleStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SaleResponse(
        String code,
        SaleStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<SaleItemResponse> items
) {
}
