package com.devcaiqueoliveira.mercatopdvsystem.service;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercatopdvsystem.repository.ProductRepository;
import com.devcaiqueoliveira.mercatopdvsystem.service.validator.ProductValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final List<ProductValidatorStrategy> validators;

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com o ID: " + id + " não encontrado."));
    }

    public List<Product> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Product create(Product product, Long categoryId) {

        validators.forEach(v -> v.validationCreate(product));

        product.setActive(true);

        Category category = categoryService.findById(categoryId);
        product.setCategory(category);

        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, Product newData, Long categoryId) {
        Product existingProduct = findById(id);

        validators.forEach(v -> v.validationUpdate(newData, id));

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
}