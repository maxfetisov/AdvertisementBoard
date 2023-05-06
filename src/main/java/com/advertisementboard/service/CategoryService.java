package com.advertisementboard.service;

import com.advertisementboard.data.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {

    Long createCategory(CategoryDto category);

    void deleteCategory(Long id);

    void updateCategory(CategoryDto category);

    List<CategoryDto> getCategories();

    CategoryDto getCategory(Long id);

}
