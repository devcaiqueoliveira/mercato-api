package com.devcaiqueoliveira.mercatopdvsystem.service;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import com.devcaiqueoliveira.mercatopdvsystem.exception.BusinessRuleException;
import com.devcaiqueoliveira.mercatopdvsystem.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.CategoryRepository;
import com.devcaiqueoliveira.mercatopdvsystem.repository.ProductRepository;
import com.devcaiqueoliveira.mercatopdvsystem.service.validator.category.CategoryValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository repository;
    private final List<CategoryValidatorStrategy> validators;
    private final ProductRepository productRepository;

    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID: " + id + " não encontrada."));
    }

    public List<Category> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Category create(Category category) {

        validators.forEach(v -> v.validationCreate(category));

        if (category.getActive() == null) {
            category.setActive(true);
        }

        return repository.save(category);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada para ser removida");
        }

        if (productRepository.existsByCategoryId(id)) {
            throw new BusinessRuleException("Não é possível excluir a categoria pois existem produtos vinculados a ela.");
        }

        repository.deleteById(id);
    }

    @Transactional
    public Category update(Long id, Category newData) {
        Category existingCategory = findById(id);

        validators.forEach(v -> v.validationUpdate(newData, id));

        existingCategory.updateFrom(newData);

        return repository.save(existingCategory);
    }

}