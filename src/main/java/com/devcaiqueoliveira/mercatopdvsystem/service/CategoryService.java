package com.devcaiqueoliveira.mercatopdvsystem.service;

import com.devcaiqueoliveira.mercatopdvsystem.model.Category;
import com.devcaiqueoliveira.mercatopdvsystem.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.CategoryRepository;
import com.devcaiqueoliveira.mercatopdvsystem.repository.ProductRepository;
import com.devcaiqueoliveira.mercatopdvsystem.service.validator.CategoryValidatorStrategy;
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
    private final ProductRepository productRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID: " + id + " não encontrada."));
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category create(Category category) {

        categoryValidators.forEach(v -> v.validationCreate(category));

        if (category.getActive() == null) {
            category.setActive(true);
        }

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Long id) {
        categoryValidators.forEach(v -> v.validationDelete(id));

        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category update(Long id, Category newData) {
        Category existingCategory = findById(id);

        categoryValidators.forEach(v -> v.validationUpdate(newData, id));

        existingCategory.updateFrom(newData);

        return categoryRepository.save(existingCategory);
    }

}