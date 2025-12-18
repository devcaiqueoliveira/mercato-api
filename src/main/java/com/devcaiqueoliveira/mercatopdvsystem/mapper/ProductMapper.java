package com.devcaiqueoliveira.mercatopdvsystem.mapper;

import com.devcaiqueoliveira.mercatopdvsystem.dto.ProductRequest;
import com.devcaiqueoliveira.mercatopdvsystem.dto.ProductResponse;
import com.devcaiqueoliveira.mercatopdvsystem.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductRequest productRequest);

    ProductResponse toProductResponse(Product product);

}
