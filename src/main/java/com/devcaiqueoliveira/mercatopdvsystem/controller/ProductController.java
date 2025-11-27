package com.devcaiqueoliveira.mercatopdvsystem.controller;

import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductRequest;
import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductResponse;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.mapper.CategoryMapper;
import com.devcaiqueoliveira.mercatopdvsystem.mapper.ProductMapper;
import com.devcaiqueoliveira.mercatopdvsystem.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listAllProducts() {
        List<Product> products = service.listAll();

        List<ProductResponse> responses = products.stream()
                .map(ProductMapper::toProductResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(ProductMapper.toProductResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        Product product = ProductMapper.toEntity(request);

        Product savedProduct = service.create(product, request.categoryId());

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toProductResponse(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        Product newData = ProductMapper.toEntity(request);

        Product updatedEntity = service.update(id, newData,  request.categoryId());

        return ResponseEntity.ok(ProductMapper.toProductResponse(updatedEntity));
    }
}