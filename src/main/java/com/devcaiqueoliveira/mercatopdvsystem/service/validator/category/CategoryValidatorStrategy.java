package com.devcaiqueoliveira.mercatopdvsystem.service.validator.category;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;

public interface CategoryValidatorStrategy {
    default void validationDelete(Long id) {};
    default void validationCreate(Category category) {};
    default void validationUpdate(Category category, Long id) {};
}