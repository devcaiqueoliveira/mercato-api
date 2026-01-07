package com.devcaiqueoliveira.mercato.repository;

import com.devcaiqueoliveira.mercato.IntegrationTest;
import com.devcaiqueoliveira.mercato.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRepositoryTest extends IntegrationTest {

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar categoria no banco de dados via TestContainer")
    void testSalvarCategoria() {
        Category category = new Category();
        category.setName("Test Container");

        Category saved = repository.save(category);

        assertThat(saved.getId()).isNotNull();
        System.out.println("ID salvo no container: " + saved.getId());
    }

    @Test
    @DisplayName("Deve retornar a categoria quando buscar pelo nome existente")
    void shouldFindByNameSuccessfully() {
        createAndSaveCategory("Bebidas");

        Category found = repository.findByName("Bebidas");

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Bebidas");
    }

    @Test
    @DisplayName("Deve retornar null, quando buscar por um nome de categoria inexistente")
    void shouldReturnNullWhenNameDoesNotExist() {
        Category found = repository.findByName("Inexistente");

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Deve retornar true, quando o nome da categoria já existir.")
    void shouldReturnTrueWhenNameExists() {
        createAndSaveCategory("Padaria");

        boolean exists = repository.existsByName("Padaria");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false, se o nome nao existir")
    void shouldReturnFalseWhenNameDoesNotExists() {
        boolean exists = repository.existsByName("Eletronicos");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve retornar true, quando o nome existe em outro registro (conflito)")
    void shouldReturnTrueWhenNameExistsOnAnotherId() {
        Category bebidas = createAndSaveCategory("Bebidas");
        Category alimentos = createAndSaveCategory("Alimentos");

        boolean exists = repository.existsByNameAndIdNot("Bebidas", alimentos.getId());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false, quando o nome existe apenas no proprio registro (sem conflito")
    void shouldReturnFalseWhenNameExistsOnSameId() {
        Category bebidas = createAndSaveCategory("Bebidas");

        boolean exists = repository.existsByNameAndIdNot("Bebidas", bebidas.getId());

        assertThat(exists).isFalse();
    }

    private Category createAndSaveCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setActive(true);
        return repository.save(category);
    }
}
