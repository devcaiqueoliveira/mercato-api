package com.devcaiqueoliveira.mercato.service.validator.impl;

import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.exception.BusinessRuleException;
import com.devcaiqueoliveira.mercato.repository.CategoryRepository;
import com.devcaiqueoliveira.mercato.service.validator.CategoryValidatorStrategy;
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
