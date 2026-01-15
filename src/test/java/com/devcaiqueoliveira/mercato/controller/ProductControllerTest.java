package com.devcaiqueoliveira.mercato.controller;

import com.devcaiqueoliveira.mercato.dto.CategoryResponse;
import com.devcaiqueoliveira.mercato.dto.ProductRequest;
import com.devcaiqueoliveira.mercato.dto.ProductResponse;
import com.devcaiqueoliveira.mercato.entity.Product;
import com.devcaiqueoliveira.mercato.mapper.ProductMapper;
import com.devcaiqueoliveira.mercato.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductMapper mapper;

    @Test
    @DisplayName("Deve criar produto e retornar 201 created")
    void shouldCreateAndReturnCreatedStatus() throws Exception {
        ProductRequest request = buildValidRequest();
        ProductResponse response = buildValidResponse();
        Product product = Product.builder().id(1L).build();

        when(mapper.toProduct(any(ProductRequest.class))).thenReturn(product);
        when(productService.create(any(Product.class))).thenReturn(product);
        when(mapper.toProductResponse(any(Product.class))).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Produto Teste"))
                .andExpect(jsonPath("$.salePrice").value(25.00));
    }

    @Test
    @DisplayName("Deve atualizar dados do produto e retornar 200 ok")
    void shouldUpdateProductAndReturnStatusOk() throws Exception {
        Long id = 1L;
        ProductRequest request = buildValidRequest();
        ProductResponse response = buildValidResponse();
        Product product = Product.builder().id(id).build();

        when(mapper.toProduct(any(ProductRequest.class))).thenReturn(product);

        when(productService.update(eq(id), any(Product.class), eq(request.categoryId()))).thenReturn(product);

        when(mapper.toProductResponse(any(Product.class))).thenReturn(response);

        mockMvc.perform(put("/api/products/{id}", id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Produto Teste"));
    }

    private ProductRequest buildValidRequest() {
        return ProductRequest.builder()
                .name("Produto Teste")
                .description("Descrição do produto")
                .barCode("1234567890")
                .sku("SKU-123")
                .costPrice(new BigDecimal("10.00"))
                .salePrice(new BigDecimal("25.00"))
                .stockQuantity(new BigDecimal("100.000"))
                .unitOfMeasure("UN")
                .categoryId(1L)
                .active(true)
                .build();
    }

    private ProductResponse buildValidResponse() {
        CategoryResponse catResponse = new CategoryResponse(1L, "Categoria A", true);

        return ProductResponse.builder()
                .id(1L)
                .name("Produto Teste")
                .description("Descrição do produto")
                .barCode("1234567890")
                .sku("SKU-123")
                .costPrice(new BigDecimal("10.00"))
                .salePrice(new BigDecimal("25.00"))
                .stockQuantity(new BigDecimal("100.000"))
                .unitOfMeasure("UN")
                .active(true)
                .category(catResponse)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
