package com.advertisementboard.service.advertisement;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageRequestDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageResponseDto;
import com.advertisementboard.data.dto.user.UserDto;

import java.util.List;

public interface AdvertisementService {

    AdvertisementDto getAdvertisement(Long id);

    List<AdvertisementDto> getAdvertisements();

    AdvertisementPageResponseDto getAdvertisements(AdvertisementPageRequestDto request);

    Long createAdvertisement(AdvertisementDto advertisement, UserDto user);

    void updateAdvertisement(AdvertisementDto advertisement, UserDto user);

    void deleteAdvertisement(Long id);

    void rejectAdvertisement(Long id);

    void confirmAdvertisement(Long id);

}
