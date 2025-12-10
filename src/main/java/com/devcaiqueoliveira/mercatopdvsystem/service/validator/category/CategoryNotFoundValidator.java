package com.devcaiqueoliveira.mercatopdvsystem.service.validator.category;

import com.devcaiqueoliveira.mercatopdvsystem.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.CategoryRepository;
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
