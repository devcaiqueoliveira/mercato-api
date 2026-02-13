package com.devcaiqueoliveira.mercato.controller;

import com.devcaiqueoliveira.mercato.IntegrationTest;
import com.devcaiqueoliveira.mercato.dto.SaleItemRequest;
import com.devcaiqueoliveira.mercato.dto.SaleRequest;
import com.devcaiqueoliveira.mercato.entity.Product;
import com.devcaiqueoliveira.mercato.repository.ProductRepository;
import com.devcaiqueoliveira.mercato.repository.SaleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class SaleControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    private Product produtoEstoque;

    @BeforeEach
    void setUp() {
        saleRepository.deleteAll();
        productRepository.deleteAll();

        produtoEstoque = Product.builder()
                .name("Coca Cola")
                .stockQuantity(new BigDecimal("10.00"))
                .active(true)
                .salePrice(new BigDecimal("5.00"))
                .build();

        produtoEstoque = productRepository.save(produtoEstoque);
    }

    @Test
    @DisplayName("Integração: Deve criar venda e baixar estoque no banco")
    void shouldCreateSaleAndDecreaseStockReal() throws Exception {
        SaleItemRequest itemRequest = SaleItemRequest.builder()
                .productId(produtoEstoque.getId())
                .quantity(new BigDecimal("3.00"))
                .build();

        SaleRequest request = SaleRequest.builder()
                .items(List.of(itemRequest))
                .build();

        mockMvc.perform(post("/api/sales")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items[0].productName").value("Coca Cola"))
                .andExpect(jsonPath("$.totalAmount").value(15.0));

        Product produtoAtualizado = productRepository.findById(produtoEstoque.getId()).orElseThrow();

        assertThat(produtoAtualizado.getStockQuantity()).isEqualByComparingTo("7.00");
    }

    @Test
    @DisplayName("Integração: Deve impedir venda sem estoque (Rollback)")
    void shouldPreventSaleWhenStockIsInsufficient() throws Exception {
        SaleItemRequest itemRequest = SaleItemRequest.builder()
                .productId(produtoEstoque.getId())
                .quantity(new BigDecimal("20.00"))
                .build();

        SaleRequest request = SaleRequest.builder()
                .items(List.of(itemRequest))
                .build();

        mockMvc.perform(post("/api/sales")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        Product produtoIntacto = productRepository.findById(produtoEstoque.getId()).orElseThrow();
        assertThat(produtoIntacto.getStockQuantity()).isEqualByComparingTo("10.00");
    }
}