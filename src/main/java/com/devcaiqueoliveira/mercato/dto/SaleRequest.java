package com.devcaiqueoliveira.mercato.dto;

import com.devcaiqueoliveira.mercato.entity.SaleItem;
import com.devcaiqueoliveira.mercato.enums.SaleStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
public record SaleRequest(

        @NotBlank(message = "O código do produto é obrigatório")
        String code,

        @NotBlank(message = "O Status da compra é obrigatório")
        SaleStatus status,

        BigDecimal totalAmount,

        List<SaleItem> items,

        LocalDateTime createdAt,

        LocalDateTime updatedAt



        ) {


}
