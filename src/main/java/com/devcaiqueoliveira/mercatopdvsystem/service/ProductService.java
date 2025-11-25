package com.devcaiqueoliveira.mercatopdvsystem.service;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
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
                .orElseThrow(() -> new RuntimeException("Produto com o ID: " + id + " não encontrado."));
    }

    public List<Product> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Product save(Product product) {

        Optional<Product> existingProductOpt = repository.findByBarCode(product.getBarCode());

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            if (!existingProduct.getId().equals(product.getId())) {
                throw new RuntimeException("Já existe um outro produto com este código de barras");
            }
        }
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category validCategory = categoryService.findById(product.getCategory().getId());
            product.setCategory(validCategory);
        }

        return repository.save(product);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado no sistema.");
        }
        repository.deleteById(id);
    }
}