package com.devcaiqueoliveira.mercato.service;

import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.entity.Product;
import com.devcaiqueoliveira.mercato.repository.ProductRepository;
import com.devcaiqueoliveira.mercato.service.validator.ProductValidatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Product> listPerPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Product create(Product product) {

        validators.forEach(v -> v.validationCreate(product));

        if (product.getActive() == null) {
            product.setActive(true);
        }

        Category category = categoryService.findById(product.getCategory().getId());
        product.setCategory(category);

        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, Product newData, Long categoryId) {
        validators.forEach(v -> v.validationUpdate(newData, id));

        Product existingProduct = findById(id);

        existingProduct.updateFrom(newData);

        if (categoryId != null) {
            Category newCategory = categoryService.findById(categoryId);
            existingProduct.setCategory(newCategory);
        }

        return repository.save(existingProduct);
    }

    @Transactional
    public void deleteById(Long id) {
        validators.forEach(v -> v.validationDelete(id));
        repository.deleteById(id);
    }
}