package com.devcaiqueoliveira.mercatopdvsystem.repository;

import com.devcaiqueoliveira.mercatopdvsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByBarCode(String barCode);

    boolean existsByBarCode(String barCode);

    boolean existsByBarCodeAndIdNot(String barCode, Long id);

    boolean existsByCategoryId(Long categoryId);

}
