package com.devcaiqueoliveira.mercato.service.validator;

import com.devcaiqueoliveira.mercato.entity.Category;

public interface CategoryValidatorStrategy {
    default void validationDelete(Long id) {};
    default void validationCreate(Category category) {};
    default void validationUpdate(Category category) {};
}