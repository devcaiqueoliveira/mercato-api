package com.devcaiqueoliveira.mercatopdvsystem.mapper;

import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductRequest;
import com.devcaiqueoliveira.mercatopdvsystem.controller.dto.ProductResponse;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductRequest productRequest);

    ProductResponse toProductResponse(Product product);

}
