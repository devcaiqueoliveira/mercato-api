package com.devcaiqueoliveira.mercato.service.validator.impl;

import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.entity.Product;
import com.devcaiqueoliveira.mercato.repository.CategoryRepository;
import com.devcaiqueoliveira.mercato.service.validator.ProductValidatorStrategy;
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
