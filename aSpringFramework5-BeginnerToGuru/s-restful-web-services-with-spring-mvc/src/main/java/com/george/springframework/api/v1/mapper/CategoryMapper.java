package com.george.springframework.api.v1.mapper;

import com.george.springframework.api.v1.model.CategoryDTO;
import com.george.springframework.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // @Mapping(source = "getId", target = "id")
    CategoryDTO categoryToCategoryDTO(Category category);
}
