package com.devcaiqueoliveira.mercatopdvsystem.service.validator;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.exception.BusinessRuleException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UniqueBarCodeValidator implements ProductValidatorStrategy{

    private final ProductRepository repository;

    @Override
    public void validationCreate(Product product) {
        if (repository.existsByBarCode(product.getBarCode())) {
            throw new BusinessRuleException("Já existe um produto cadastrado com este código de barras.");
        }
    }

    @Override
    public void validationUpdate(Product product, Long id) {
        if (repository.existsByBarCodeAndIdNot(product.getBarCode(), id)) {
            throw new BusinessRuleException("Este código de barras pertence a outro produto.");
        }
    }
}
