package com.devcaiqueoliveira.mercatopdvsystem.service.validator;

import com.devcaiqueoliveira.mercatopdvsystem.model.Category;

public interface CategoryValidatorStrategy {
    default void validationDelete(Long id) {};
    default void validationCreate(Category category) {};
    default void validationUpdate(Category category, Long id) {};
}