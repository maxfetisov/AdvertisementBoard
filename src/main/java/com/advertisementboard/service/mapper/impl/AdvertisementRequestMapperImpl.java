package com.advertisementboard.service.mapper.impl;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementRequestDto;
import com.advertisementboard.service.category.CategoryService;
import com.advertisementboard.service.user.UserService;
import com.advertisementboard.service.mapper.AdvertisementRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementRequestMapperImpl implements AdvertisementRequestMapper {

    private final CategoryService categoryService;

    private final UserService userService;

    @Override
    public AdvertisementDto advertisementRequestDtoToAdvertisementDto(AdvertisementRequestDto advertisementRequestDto) {
        return AdvertisementDto.builder()
                .heading(advertisementRequestDto.getHeading())
                .text(advertisementRequestDto.getText())
                .category(categoryService.getCategory(advertisementRequestDto.getCategoryId()))
                .build();
    }

    @Override
    public AdvertisementRequestDto advertisementDtoToAdvertisementRequestDto(AdvertisementDto advertisementDto) {
        return AdvertisementRequestDto.builder()
                .heading(advertisementDto.getHeading())
                .text(advertisementDto.getText())
                .categoryId(advertisementDto.getCategory().getId())
                .build();
    }

}
