package com.devcaiqueoliveira.mercato.mapper;

import com.devcaiqueoliveira.mercato.dto.CategoryRequest;
import com.devcaiqueoliveira.mercato.dto.CategoryResponse;
import com.devcaiqueoliveira.mercato.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toCategoryResponse(Category category);
}