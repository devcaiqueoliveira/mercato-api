package com.devcaiqueoliveira.mercatopdvsystem.controller;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import com.devcaiqueoliveira.mercatopdvsystem.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> listAllProducts() {
        return ResponseEntity.ok(service.listAll());
    }
}