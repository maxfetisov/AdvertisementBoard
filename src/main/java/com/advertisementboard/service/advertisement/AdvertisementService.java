package com.advertisementboard.service.advertisement;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageRequestDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageResponseDto;

import java.util.List;

public interface AdvertisementService {

    AdvertisementDto getAdvertisement(Long id);

    List<AdvertisementDto> getAdvertisements();

    AdvertisementPageResponseDto getAdvertisements(AdvertisementPageRequestDto request);

    Long createAdvertisement(AdvertisementDto advertisement);

    void updateAdvertisement(AdvertisementDto advertisement);

    void deleteAdvertisement(Long id);

}
