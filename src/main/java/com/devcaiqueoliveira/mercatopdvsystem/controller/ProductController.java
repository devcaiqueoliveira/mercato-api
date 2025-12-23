package com.devcaiqueoliveira.mercatopdvsystem.controller;

import com.devcaiqueoliveira.mercatopdvsystem.dto.ProductRequest;
import com.devcaiqueoliveira.mercatopdvsystem.dto.ProductResponse;
import com.devcaiqueoliveira.mercatopdvsystem.mapper.ProductMapper;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de estoque e catálogo.")
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @Operation(summary = "Listar produtos cadastrados no sistema por página.")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> listProducts(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<Product> products = service.listPerPage(pageable);

        Page<ProductResponse> responses = products
                .map(mapper::toProductResponse);

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

        Product savedProduct = service.create(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toProductResponse(savedProduct));
    }

    @Operation(summary = "Atualiza um produto existente no sistema.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        Product newData = mapper.toProduct(request);

        Product updatedEntity = service.update(id, newData, request.categoryId());

        return ResponseEntity.ok(mapper.toProductResponse(updatedEntity));
    }

    @Operation(summary = "Remove um produto existente do sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}