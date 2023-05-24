package com.advertisementboard.data.dto.advertisement;

import com.advertisementboard.data.dto.category.CategoryDto;
import com.advertisementboard.data.dto.user.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class AdvertisementDto {

    private Long id;

    @NonNull
    private String heading;

    private String text;

    @NonNull
    private UserDto user;

    @NonNull
    private CategoryDto category;

}