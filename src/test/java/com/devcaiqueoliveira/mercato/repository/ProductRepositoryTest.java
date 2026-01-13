package com.devcaiqueoliveira.mercato.repository;

import com.devcaiqueoliveira.mercato.IntegrationTest;
import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductRepositoryTest extends IntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar produto e gerar ID corretamente")
    void shouldSaveProductSuccessfully() {
        Category category = createAndSaveCategory("Geral");
        Product product = buildProduct("Coca Cola", "123456", category);

        Product saved = productRepository.save(product);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull(); // Testando Auditing
        System.out.println("Produto salvo ID: " + saved.getId());
    }

    private Category createAndSaveCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .active(true)
                .build();
        return categoryRepository.save(category);
    }

    private Product createAndSaveProduct(String name, String barCode, Category category) {
        Product product = buildProduct(name, barCode, category);
        return productRepository.save(product);
    }

    private Product buildProduct(String name, String barCode, Category category) {
        return Product.builder()
                .name(name)
                .barCode(barCode)
                .category(category)
                .salePrice(BigDecimal.TEN)
                .costPrice(BigDecimal.ONE)
                .stockQuantity(BigDecimal.valueOf(100))
                .unitOfMeasure("UN")
                .active(true)
                .description("Descrição teste")
                .build();
    }

}
