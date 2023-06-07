package com.advertisementboard.service.mapper;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementRequestDto;

public interface AdvertisementRequestMapper {

    AdvertisementDto advertisementRequestDtoToAdvertisementDto(AdvertisementRequestDto advertisementRequestDto);

    AdvertisementRequestDto advertisementDtoToAdvertisementRequestDto(AdvertisementDto advertisementDto);

}
