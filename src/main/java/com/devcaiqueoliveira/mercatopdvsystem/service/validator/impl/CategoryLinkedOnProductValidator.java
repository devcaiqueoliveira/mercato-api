package com.devcaiqueoliveira.mercatopdvsystem.service.validator.impl;

import com.devcaiqueoliveira.mercatopdvsystem.exception.BusinessRuleException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.ProductRepository;
import com.devcaiqueoliveira.mercatopdvsystem.service.validator.CategoryValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryLinkedOnProductValidator implements CategoryValidatorStrategy {

    private final ProductRepository productRepository;

    @Override
    public void validationDelete(Long id) {
        if (productRepository.existsByCategoryId(id)) {
            throw new BusinessRuleException("Não é possível excluir a categoria pois existem produtos vinculados a ela.");
        }
    }
}
