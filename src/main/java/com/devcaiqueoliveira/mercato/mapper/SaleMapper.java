package com.devcaiqueoliveira.mercato.mapper;

import com.devcaiqueoliveira.mercato.dto.SaleItemRequest;
import com.devcaiqueoliveira.mercato.dto.SaleItemResponse;
import com.devcaiqueoliveira.mercato.dto.SaleRequest;
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

    Sale toSale(SaleRequest request);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    SaleItem toSaleItem(SaleItemRequest request);
}
