package com.advertisementboard.service.impl;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageRequestDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageResponseDto;
import com.advertisementboard.data.entity.Advertisement;
import com.advertisementboard.repository.AdvertisementRepository;
import com.advertisementboard.service.AdvertisementService;
import com.advertisementboard.service.mapper.AdvertisementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementMapper advertisementMapper;

    @Override
    public AdvertisementDto getAdvertisement(Long id) {
        return advertisementMapper.advertisementToAdvertisementDto(advertisementRepository.findById(id)
                .orElseThrow(RuntimeException::new));//TODO свое исплючение
    }

    @Override
    public List<AdvertisementDto> getAdvertisements() {
        return advertisementRepository.findAll().stream()
                .map(advertisementMapper::advertisementToAdvertisementDto)
                .toList();
    }

    @Override
    public AdvertisementPageResponseDto getAdvertisements(AdvertisementPageRequestDto request) {
        Page<Advertisement> advertisements;
        Pageable pageable;
        if (request.getSortField() == null || request.getSortField().isEmpty()) {
            pageable = PageRequest.of(request.getPage(), request.getPageSize());
        } else {
            pageable = PageRequest.of(
                    request.getPage(),
                    request.getPageSize(),
                    Sort.by(request.getSortField()
                    ));
        }
        if(request.getCategoryFilter() != null && request.getUserFilter() != null && !request.getUserFilter().isEmpty()){
            advertisements = advertisementRepository.findAllByUserLoginAndAndCategoryId(request.getUserFilter(), request.getCategoryFilter(), pageable);
        } else if (request.getCategoryFilter() != null) {
            advertisements = advertisementRepository.findAllByCategoryId(request.getCategoryFilter(), pageable);
        }
        else if(request.getUserFilter() != null && !request.getUserFilter().isEmpty()){
            advertisements = advertisementRepository.findAllByUserLogin(request.getUserFilter(), pageable);
        }
        else{
            advertisements = advertisementRepository.findAll(pageable);
        }
        return AdvertisementPageResponseDto.builder()
                .currentPage(request.getPage())
                .pageSize(request.getPageSize())
                .pageCount(advertisements.getTotalPages())
                .elementsCount(advertisements.getTotalElements())
                .advertisements(advertisements.getContent().stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .toList())
                .build();
    }

}
