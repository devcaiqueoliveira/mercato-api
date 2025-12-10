package com.devcaiqueoliveira.mercatopdvsystem.service.validator.impl;

import com.devcaiqueoliveira.mercatopdvsystem.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.ProductRepository;
import com.devcaiqueoliveira.mercatopdvsystem.service.validator.ProductValidatorStrategy;
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
