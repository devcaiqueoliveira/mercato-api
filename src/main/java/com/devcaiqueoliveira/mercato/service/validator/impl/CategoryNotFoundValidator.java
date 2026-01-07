package com.devcaiqueoliveira.mercato.service.validator.impl;

import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.repository.CategoryRepository;
import com.devcaiqueoliveira.mercato.service.validator.CategoryValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CategoryNotFoundValidator implements CategoryValidatorStrategy {

    private final CategoryRepository repository;

    @Override
    public void validationDelete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada para ser removida");
        }
    }
}