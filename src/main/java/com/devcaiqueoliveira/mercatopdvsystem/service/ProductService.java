package com.devcaiqueoliveira.mercatopdvsystem.service;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.exception.BusinessRuleException;
import com.devcaiqueoliveira.mercatopdvsystem.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com o ID: " + id + " não encontrado."));
    }

    public List<Product> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Product create(Product product, Long categoryId) {

        validateUniqueBarCode(product.getBarCode());

        product.setActive(true);

        Category category = categoryService.findById(categoryId);
        product.setCategory(category);

        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, Product newData, Long categoryId) {
        Product existingProduct = findById(id);

        validateUniqueBarCodeForUpdate(newData.getBarCode(), id);

        existingProduct.updateFrom(newData);

        if (categoryId != null) {
            Category newCategory = categoryService.findById(categoryId);
            existingProduct.setCategory(newCategory);
        }

        return repository.save(existingProduct);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado no sistema.");
        }
        repository.deleteById(id);
    }

    private void validateUniqueBarCode(String barCode) {
        if (repository.existsByBarCode(barCode)) {
            throw new BusinessRuleException("Já existe um produto cadastrado com este código de barras");
        }
    }

    private void validateUniqueBarCodeForUpdate(String barCode, Long id) {
        if (repository.existsByBarCodeAndIdNot(barCode, id)) {
            throw new BusinessRuleException("O código de barras declarado já pertence a outro produto.");
        }
    }
}