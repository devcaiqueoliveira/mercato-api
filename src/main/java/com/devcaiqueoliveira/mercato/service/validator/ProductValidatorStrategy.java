package com.devcaiqueoliveira.mercato.service.validator;

import com.devcaiqueoliveira.mercato.entity.Product;

public interface ProductValidatorStrategy {
    default void validationDelete(Long id) { }
    default void validationCreate(Product product) { };
    default void validationUpdate(Product product, Long id) { };
}