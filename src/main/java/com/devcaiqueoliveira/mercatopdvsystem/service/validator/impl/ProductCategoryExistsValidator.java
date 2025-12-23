package com.devcaiqueoliveira.mercatopdvsystem.service.validator.impl;

import com.devcaiqueoliveira.mercatopdvsystem.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.repository.CategoryRepository;
import com.devcaiqueoliveira.mercatopdvsystem.service.validator.ProductValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductCategoryExistsValidator implements ProductValidatorStrategy {

    private final CategoryRepository categoryRepository;

    @Override
    public void validationCreate(Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("A categoria do produto é obrigatória");
        }

        Long categoryId = product.getCategory().getId();
        boolean categoryExists = categoryRepository.existsById(categoryId);

        if (!categoryExists) {
            throw new EntityNotFoundException("A categoria informada não foi encontrada.");
        }
    }
}
