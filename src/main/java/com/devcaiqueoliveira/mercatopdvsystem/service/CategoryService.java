package com.devcaiqueoliveira.mercatopdvsystem.service;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import com.devcaiqueoliveira.mercatopdvsystem.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository repository;

    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
    }

    public List<Category> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Category save(Category category) {
        Category existingCategory = repository.findByName(category.getName());
        if (existingCategory != null && !existingCategory.getId().equals(category.getId())) {
            throw new RuntimeException("Categoria com o nome " + category.getName() + " já existe.");
        }
        return repository.save(category);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada para ser removida");
        }
        repository.deleteById(id);
    }

}