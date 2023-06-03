package com.advertisementboard.service.impl;

import com.advertisementboard.data.dto.category.CategoryDto;
import com.advertisementboard.exception.entity.EntityAlreadyExistException;
import com.advertisementboard.exception.entity.EntityNotExistException;
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
    public Long createCategory(final CategoryDto category) {
        if (category.getId() != null && categoryRepository.existsById(category.getId()))
            throw new EntityAlreadyExistException(category.toString());
        return categoryRepository.save(categoryMapper.categoryDtoToCategory(category)).getId();
    }

    @Override
    public void deleteCategory(final Long id) {
        if (!categoryRepository.existsById(id))
            throw new EntityNotExistException(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateCategory(final CategoryDto category) {
        categoryRepository.findById(category.getId()).ifPresentOrElse(oldCategory ->
                        categoryRepository.save(categoryMapper.categoryDtoToCategory(category)),
                () -> {
                    throw new EntityNotExistException(category.getId());
                });
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(final Long id) {
        return categoryMapper.categoryToCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistException(id)));
    }

}
