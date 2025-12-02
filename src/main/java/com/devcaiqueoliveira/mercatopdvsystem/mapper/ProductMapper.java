package com.devcaiqueoliveira.mercatopdvsystem.mapper;

import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductRequest;
import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductResponse;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;

public final class ProductMapper {

    public static Product toEntity(ProductRequest request) {

        return Product.builder()
                .name(request.name())
                .description(request.description())
                .barCode(request.barCode())
                .sku(request.sku())
                .costPrice(request.costPrice())
                .salePrice(request.salePrice())
                .stockQuantity(request.stockQuantity())
                .unitOfMeasure(request.unitOfMeasure())
                .ncmCode(request.ncmCode())
                .build();


    }

    public static ProductResponse toProductResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getBarCode(),
                product.getSku(),
                product.getSalePrice(),
                product.getStockQuantity(),
                product.getUnitOfMeasure(),
                product.getActive(),
                product.getCategory() != null ? CategoryMapper.toCategoryResponse(product.getCategory()) : null,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
