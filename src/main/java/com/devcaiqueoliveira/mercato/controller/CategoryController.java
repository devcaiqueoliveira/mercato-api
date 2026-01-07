package com.devcaiqueoliveira.mercato.controller;

import com.devcaiqueoliveira.mercato.dto.CategoryRequest;
import com.devcaiqueoliveira.mercato.dto.CategoryResponse;
import com.devcaiqueoliveira.mercato.mapper.CategoryMapper;
import com.devcaiqueoliveira.mercato.entity.Category;
import com.devcaiqueoliveira.mercato.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@Tag(name = "Categorias", description = "Gestão de departamentos.")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Lista todas as categorias cadastradas no sistema.")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.listAll();

        List<CategoryResponse> responses = categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Encontra uma categoria associada a um ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(category));
    }

    @Operation(summary = "Cria uma nova categoria.")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        Category savedCategory = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.toCategoryResponse(savedCategory));
    }

    @Operation(summary = "Atualiza uma categoria existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        Category newData = categoryMapper.toCategory(request);

        Category updatedCategory = categoryService.update(id, newData);

        return ResponseEntity.ok(categoryMapper.toCategoryResponse(updatedCategory));
    }

    @Operation(summary = "Remove uma categoria existente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}