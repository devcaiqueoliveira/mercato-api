package com.devcaiqueoliveira.mercato.service;

import com.devcaiqueoliveira.mercato.entity.Category;
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



}
