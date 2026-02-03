package com.devcaiqueoliveira.mercato.controller;

import com.devcaiqueoliveira.mercato.dto.SaleRequest;
import com.devcaiqueoliveira.mercato.dto.SaleResponse;
import com.devcaiqueoliveira.mercato.entity.Sale;
import com.devcaiqueoliveira.mercato.mapper.SaleMapper;
import com.devcaiqueoliveira.mercato.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@AllArgsConstructor
@Tag(name = "Vendas", description = "Gerenciamento de transações e caixa.")
public class SaleController {

    private final SaleService saleService;
    private final SaleMapper saleMapper;

    @Operation(summary = "Registrar uma nova venda.")
    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody @Valid SaleRequest request) {

        Sale saleEntity = saleMapper.toSale(request);

        Sale savedSale = saleService.create(saleEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saleMapper.toSaleResponse(savedSale));

    }

    @Operation(summary = "Buscar venda por ID.")
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        return ResponseEntity.ok(saleMapper.toSaleResponse(sale));
    }

}
