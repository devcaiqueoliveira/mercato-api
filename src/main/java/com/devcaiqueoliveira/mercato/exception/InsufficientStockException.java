package com.devcaiqueoliveira.mercato.exception;

public class InsufficientStockException extends BusinessRuleException {
    public InsufficientStockException(String productName) {
        super("Estoque insuficiente para o produto: " + productName);
    }
}
