package com.devcaiqueoliveira.mercato.repository;

import com.devcaiqueoliveira.mercato.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByCode(String code);
}
