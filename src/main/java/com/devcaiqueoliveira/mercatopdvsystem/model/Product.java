package com.devcaiqueoliveira.mercatopdvsystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
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

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "bar_code", unique = true, length = 20)
    private String barCode;

    private String sku;

    @Column(name = "cost_price", precision = 19, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "sale_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "stock_quantity", nullable = false, precision = 19, scale = 3)
    private BigDecimal stockQuantity;

    @Column(name = "unit_of_measure", nullable = false, length = 5)
    private String unitOfMeasure;

    @Column(name = "ncm_code", length = 8)
    private String ncmCode;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Boolean active = true;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateFrom(Product newData) {
        this.name = newData.getName();
        this.description = newData.getDescription();
        this.barCode = newData.getBarCode();
        this.sku = newData.getSku();
        this.costPrice = newData.getCostPrice();
        this.salePrice = newData.getSalePrice();
        this.stockQuantity = newData.getStockQuantity();
        this.unitOfMeasure = newData.getUnitOfMeasure();
        this.ncmCode = newData.getNcmCode();


        if (newData.getActive() != null) {
            this.active = newData.getActive();
        }
    }

}
