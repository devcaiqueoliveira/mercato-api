package com.devcaiqueoliveira.mercato.service;

import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.repository.CategoryRepository;
import com.devcaiqueoliveira.mercato.service.validator.CategoryValidatorStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private List<CategoryValidatorStrategy> validators;

    @InjectMocks
    private CategoryService service;

    @Test
    @DisplayName("Deve salvar a categoria e ativar flag 'active' automaticamente se vier nula")
    void shouldCreateCategoryAndSetActiveTrue() {
        Category category = new Category();
        category.setName("Bebidas");
        category.setActive(null);

        when(repository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        Category saved = service.create(category);

        assertTrue(saved.getActive(), "O service deveria ter forçado 'active' = true");

        verify(repository).save(category);
    }

    @Test
    @DisplayName("Deve buscar por Id com sucesso")
    void shouldFindByIdSuccessfully() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(category));

        Category result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao buscar Id inexistente")
    void shouldThrowExceptionWhenIdNotFound() {
        Long fakeId = 1L;

        when(repository.findById(fakeId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(fakeId));

        verify(repository).findById(fakeId);
    }

    @Test
    @DisplayName("Deve chamar as validacoes antes de deletar")
    void shouldCallValidatorsBeforeDelete() {
        Long id = 1L;

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve atualizar a categoria informada com sucesso")
    void shouldUpdateCategory() {
        Long id = 1L;

        Category existingCategory = new Category();
        existingCategory.setId(id);
        existingCategory.setName("Nome Antigo");
        existingCategory.setActive(true);

        Category newCategoryData = new Category();
        newCategoryData.setName("Nome Atualizado");
        newCategoryData.setActive(true);

        when(repository.findById(id)).thenReturn(Optional.of(existingCategory));

        when(repository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        Category result = service.update(id, newCategoryData);

        assertNotNull(result);

        assertEquals(id, result.getId());

        verify(repository).save(any(Category.class));

    }

    @Test
    @DisplayName("Deve retornar a lista com as categorias com sucesso")
    void shouldReturnAllCategories() {

        List<Category> categories = List.of(new Category(), new Category());

        when(repository.findAll()).thenReturn(categories);

        List<Category> result = service.listAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository).findAll();

    }


}
