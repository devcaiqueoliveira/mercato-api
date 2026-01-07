package com.devcaiqueoliveira.mercato.repository;

import com.devcaiqueoliveira.mercato.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    boolean existsByBarCode(String barCode);

    boolean existsByBarCodeAndIdNot(String barCode, Long id);

    boolean existsByCategoryId(Long categoryId);

}
