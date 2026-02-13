package com.devcaiqueoliveira.mercato.repository;

import com.devcaiqueoliveira.mercato.IntegrationTest;
import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(saved.getCreatedAt()).isNotNull();
        System.out.println("Produto salvo ID: " + saved.getId());
    }

    @Test
    @DisplayName("Deve retornar true se existe produto com o barcode informado")
    void shouldReturnTrueWhenBarCodeExists() {
        Category category = createAndSaveCategory("Bebidas");
        createAndSaveProduct("Suco", "CODE_123", category);

        boolean exists = productRepository.existsByBarCode("CODE_123");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false se o barcode nao existir")
    void shouldReturnFalseWhenBarCodeDoesNotExist() {
        boolean exists = productRepository.existsByBarCode("NAO_EXISTE");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve retornar true quando barcode existe em OUTRO produto (conflito de update)")
    void shouldReturnTrueWhenBarCodeExistsOnAnotherId() {
        Category category = createAndSaveCategory("Diversos");
        Product p1 = createAndSaveProduct("Produto 1", "BARCODE_X", category);
        Product p2 = createAndSaveProduct("Produto 2", "BARCODE_Y", category);

        boolean exists = productRepository.existsByBarCodeAndIdNot("BARCODE_X", p2.getId());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso ao quando barCode pertence ao proprio produto (evitando conflito no update)")
    void shouldReturnFalseWhenBarCodeBelongsToSameId() {
        Category category = createAndSaveCategory("Diversos");
        Product p1 = createAndSaveProduct("Produto 1", "BARCODE_X", category);

        boolean exists = productRepository.existsByBarCodeAndIdNot("BARCODE_X", p1.getId());

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve verificar se existe algum produto vinculado a uma categoria")
    void shouldCheckIfProductExistsByCategory() {
        Category categoryWithProduct = createAndSaveCategory("Com Produto");
        createAndSaveProduct("Item A","A1", categoryWithProduct);

        Category emptyCategory = createAndSaveCategory("Vazia");

        assertThat(productRepository.existsByCategoryId(categoryWithProduct.getId())).isTrue();
        assertThat(productRepository.existsByCategoryId(emptyCategory.getId())).isFalse();
    }

    @Test
    @DisplayName("Deve listar produtos com paginação")
    void shouldListProductsPaged() {
        Category category = createAndSaveCategory("Paginação");
        createAndSaveProduct("P1", "111", category);
        createAndSaveProduct("P2", "222", category);
        createAndSaveProduct("P3", "333", category);

        Page<Product> page = productRepository.findAll(PageRequest.of(0, 2));

        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent()).hasSize(2);
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
                .stockQuantity(BigDecimal.valueOf(100))
                .active(true)
                .build();
    }

}
