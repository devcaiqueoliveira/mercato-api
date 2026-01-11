package com.devcaiqueoliveira.mercato.service;

import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.repository.CategoryRepository;
import com.devcaiqueoliveira.mercato.service.validator.CategoryValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final List<CategoryValidatorStrategy> categoryValidators;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID: " + id + " não encontrada."));
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public Category create(Category category) {

        categoryValidators.forEach(v -> v.validationCreate(category));

        if (category.getActive() == null) {
            category.setActive(true);
        }

        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryValidators.forEach(v -> v.validationDelete(id));

        categoryRepository.deleteById(id);
    }

    public Category update(Long id, Category newData) {
        Category existingCategory = findById(id);

        existingCategory.setName(newData.getName());
        existingCategory.setActive(newData.getActive());

        categoryValidators.forEach(v -> v.validationUpdate(existingCategory));

        return categoryRepository.save(existingCategory);
    }

}