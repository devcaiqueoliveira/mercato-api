package com.devcaiqueoliveira.mercato.service;

import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.entity.Product;
import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.repository.ProductRepository;
import com.devcaiqueoliveira.mercato.service.validator.ProductValidatorStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Spy
    private List<ProductValidatorStrategy> validators = new ArrayList<>();

    @Mock
    private  ProductValidatorStrategy validatorMock;

    @Test
    @DisplayName("Deve criar ao produto com sucesso após validações")
    void shouldCreateProductSuccessfully() {
        validators.add(validatorMock);

        Product inputProduct = buildProduct();
        inputProduct.setId(null);

        Product savedProduct = buildProduct();

        Category categoryFound = Category.builder().id(1L).name("Categoria Teste").build();

        when(categoryService.findById(1L)).thenReturn(categoryFound);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product createdProduct = productService.create(inputProduct);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId()).isEqualTo(1L);
        assertThat(createdProduct.getActive()).isTrue();

        verify(validatorMock, times(1)).validationCreate(inputProduct);
        verify(categoryService, times(1)).findById(1L);
        verify(productRepository, times(1)).save(inputProduct);
    }

    private Product buildProduct() {
        Category category = Category.builder()
                .id(1L)
                .name("Categoria Teste")
                .active(true)
                .build();

        return Product.builder()
                .id(1L)
                .name("Produto Teste")
                .description("Descrição do produto teste")
                .barCode("789123456789")
                .sku("SKU-TEST-001")
                .costPrice(new BigDecimal("10.50"))
                .salePrice(new BigDecimal("20.00"))
                .stockQuantity(new BigDecimal("100.000"))
                .unitOfMeasure("UN")
                .active(true)
                .category(category)
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
