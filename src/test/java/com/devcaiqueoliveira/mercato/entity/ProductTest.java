package com.devcaiqueoliveira.mercato.entity;

import com.devcaiqueoliveira.mercato.exception.BusinessRuleException;
import com.devcaiqueoliveira.mercato.exception.InsufficientStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    @DisplayName("Deve baixar o estoque corretamente quando há saldo suficiente.")
    void shouldDecreaseStockSuccessfully() {

        Product product = Product.builder()
                .name("Produto Teste")
                .stockQuantity(new BigDecimal("10.00"))
                .build();

        product.decreaseStock(new BigDecimal("3.00"));

        assertThat(product.getStockQuantity()).isEqualByComparingTo("7.00");
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar baixar mais do que o estoque disponível.")
    void shouldThrowExceptionWhenStockIsInsufficient() {

        Product product = Product.builder()
                .name("Produto Teste")
                .stockQuantity(new BigDecimal("5.00"))
                .build();

        assertThatThrownBy(() -> product.decreaseStock(new BigDecimal("6.00")))
                .isInstanceOf(InsufficientStockException.class);
    }
}
