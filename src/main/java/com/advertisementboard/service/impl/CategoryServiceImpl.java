package com.advertisementboard.service.impl;

import com.advertisementboard.data.dto.category.CategoryDto;
import com.advertisementboard.repository.CategoryRepository;
import com.advertisementboard.service.CategoryService;
import com.advertisementboard.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Long createCategory(CategoryDto category) {
        return categoryRepository.save(categoryMapper.categoryDtoToCategory(category)).getId();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateCategory(CategoryDto category) {
        categoryRepository.findById(category.getId()).ifPresentOrElse(oldCategory ->
                        categoryRepository.save(categoryMapper.categoryDtoToCategory(category)),
                () -> {throw new RuntimeException();});//TODO свое исключение
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(Long id) {
        return categoryMapper.categoryToCategoryDto(categoryRepository.findById(id).orElseThrow(RuntimeException::new));//TODO свое исключение
    }

}
