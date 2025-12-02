package com.devcaiqueoliveira.mercatopdvsystem.mapper;

import com.devcaiqueoliveira.mercatopdvsystem.dto.CategoryRequest;
import com.devcaiqueoliveira.mercatopdvsystem.dto.CategoryResponse;
import com.devcaiqueoliveira.mercatopdvsystem.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toCategoryResponse(Category category);
}
