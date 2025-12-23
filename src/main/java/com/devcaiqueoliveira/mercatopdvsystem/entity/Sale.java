package com.devcaiqueoliveira.mercatopdvsystem.entity;

import jakarta.persistence.PrePersist;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sale {

    private Long id;

    private String code;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    private List<SaleItem> items = new ArrayList<>();

    private LocalDateTime createdAt;

    @PrePersist
    public void generateCode() {
        if (this.code == null) {
            this.code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

    public void addItem() {

    }
}
