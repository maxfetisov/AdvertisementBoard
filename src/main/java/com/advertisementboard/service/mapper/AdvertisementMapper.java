package com.advertisementboard.service.mapper;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.entity.Advertisement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class})
public interface AdvertisementMapper {

    Advertisement advertisementDtoToAdvertisement(AdvertisementDto advertisementDto);

    AdvertisementDto advertisementToAdvertisementDto(Advertisement advertisement);

}
