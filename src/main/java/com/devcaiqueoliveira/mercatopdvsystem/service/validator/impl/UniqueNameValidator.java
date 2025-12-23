package com.devcaiqueoliveira.mercatopdvsystem.service.validator.impl;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import com.devcaiqueoliveira.mercatopdvsystem.exception.BusinessRuleException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.CategoryRepository;
import com.devcaiqueoliveira.mercatopdvsystem.service.validator.CategoryValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UniqueNameValidator implements CategoryValidatorStrategy {

    private final CategoryRepository  repository;

    @Override
    public void validationCreate(Category category) {
        if (repository.existsByName(category.getName())) {
            throw new BusinessRuleException("Já existe uma categoria com este nome.");
        }
    }

    @Override
    public void validationUpdate(Category category, Long id) {
        if (repository.existsByNameAndIdNot(category.getName(), id)) {
            throw new BusinessRuleException("Uma categoria com este nome já pertence a outro registro.");
        }
    }
}
