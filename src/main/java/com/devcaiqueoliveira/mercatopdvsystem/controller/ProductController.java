package com.devcaiqueoliveira.mercatopdvsystem.controller;

import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductRequest;
import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductResponse;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.mapper.ProductMapper;
import com.devcaiqueoliveira.mercatopdvsystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de estoque e catálogo.")
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @Operation(summary = "Listar todos os produtos cadastrados no sistema.")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> listAllProducts() {
        List<Product> products = service.listAll();

        List<ProductResponse> responses = products.stream()
                .map(mapper::toProductResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Encontrar produto associado a um ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(mapper.toProductResponse(product));
    }

    @Operation(summary = "Cria um produto novo no sistema.")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        Product product = mapper.toProduct(request);

        Product savedProduct = service.create(product, request.categoryId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toProductResponse(savedProduct));
    }

    @Operation(summary = "Atualiza um produto existente no sistema.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        Product newData = mapper.toProduct(request);

        Product updatedEntity = service.update(id, newData, request.categoryId());

        return ResponseEntity.ok(mapper.toProductResponse(updatedEntity));
    }
}