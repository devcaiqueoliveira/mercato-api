package com.devcaiqueoliveira.mercatopdvsystem.repository;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
}