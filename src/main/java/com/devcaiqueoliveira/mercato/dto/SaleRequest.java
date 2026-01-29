package com.devcaiqueoliveira.mercato.dto;

import com.devcaiqueoliveira.mercato.entity.SaleItem;
import com.devcaiqueoliveira.mercato.enums.SaleStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
public record SaleRequest(

        @NotEmpty(message = "Não a itens para concluir a operção.")
        @Valid
        List<SaleItem> items

        ) {


}
