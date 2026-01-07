package com.devcaiqueoliveira.mercato.service.validator.impl;

import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.repository.ProductRepository;
import com.devcaiqueoliveira.mercato.service.validator.ProductValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductNotFoundValidator implements ProductValidatorStrategy {

    private final ProductRepository productRepository;

    @Override
    public void validationDelete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado no sistema.");
        }
    }
}
