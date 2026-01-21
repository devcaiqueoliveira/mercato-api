package com.devcaiqueoliveira.mercato.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "bar_code", unique = true, length = 20)
    private String barCode;

    private String sku;

    @Column(name = "sale_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "stock_quantity", nullable = false, precision = 19, scale = 3)
    private BigDecimal stockQuantity;

    @Column(name = "unit_of_measure", nullable = false, length = 5)
    private String unitOfMeasure;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Boolean active;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateFrom(Product newData) {
        this.name = newData.getName();
        this.barCode = newData.getBarCode();
        this.sku = newData.getSku();
        this.salePrice = newData.getSalePrice();
        this.stockQuantity = newData.getStockQuantity();
        this.unitOfMeasure = newData.getUnitOfMeasure();

        if (newData.getActive() != null) {
            this.active = newData.getActive();
        }
    }
}
