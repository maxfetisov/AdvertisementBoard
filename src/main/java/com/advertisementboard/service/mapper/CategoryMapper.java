package com.advertisementboard.service.mapper;

import com.advertisementboard.data.dto.category.CategoryDto;
import com.advertisementboard.data.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category categoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToCategoryDto(Category category);

}
