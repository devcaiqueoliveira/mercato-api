package com.devcaiqueoliveira.mercato.mapper;

import com.devcaiqueoliveira.mercato.dto.SaleItemResponse;
import com.devcaiqueoliveira.mercato.dto.SaleResponse;
import com.devcaiqueoliveira.mercato.entity.Sale;
import com.devcaiqueoliveira.mercato.entity.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    SaleResponse toSaleResponse(Sale sale);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    SaleItemResponse toItemResponse(SaleItem item);
}
