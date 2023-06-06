package com.advertisementboard.service.advertisement.impl;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageRequestDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageResponseDto;
import com.advertisementboard.data.dto.user.UserDto;
import com.advertisementboard.data.entity.Advertisement;
import com.advertisementboard.data.enumeration.AdvertisementStatus;
import com.advertisementboard.exception.entity.EntityNotExistException;
import com.advertisementboard.repository.AdvertisementRepository;
import com.advertisementboard.service.advertisement.AdvertisementService;
import com.advertisementboard.service.mapper.AdvertisementMapper;
import lombok.RequiredArgsConstructor;
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
    public AdvertisementDto getAdvertisement(final Long id) {
        return advertisementMapper.advertisementToAdvertisementDto(advertisementRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistException(id)));
    }

    @Override
    public List<AdvertisementDto> getAdvertisements() {
        return advertisementRepository.findAll().stream()
                .map(advertisementMapper::advertisementToAdvertisementDto)
                .toList();
    }

    @Override
    public AdvertisementPageResponseDto getAdvertisements(final AdvertisementPageRequestDto request) {
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
        if (request.getCategoryFilter() != null
                && request.getUserFilter() != null
                && !request.getUserFilter().isEmpty()) {
            advertisements = advertisementRepository
                    .findAllByUserLoginAndCategoryId(request.getUserFilter(), request.getCategoryFilter(), pageable);
        } else if (request.getCategoryFilter() != null) {
            advertisements = advertisementRepository.findAllByCategoryId(request.getCategoryFilter(), pageable);
        } else if (request.getUserFilter() != null && !request.getUserFilter().isEmpty()) {
            advertisements = advertisementRepository.findAllByUserLogin(request.getUserFilter(), pageable);
        } else {
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

    @Override
    public Long createAdvertisement(final AdvertisementDto advertisement, final UserDto user) {
        advertisement.setUser(user);
        advertisement.setStatus(AdvertisementStatus.NOT_VERIFIED);
        return advertisementRepository.save(advertisementMapper.advertisementDtoToAdvertisement(advertisement)).getId();
    }

    @Override
    public void updateAdvertisement(final AdvertisementDto advertisement, final UserDto user) {
        advertisementRepository.findById(advertisement.getId()).ifPresentOrElse(oldCategory -> {
                    advertisement.setUser(user);
                    advertisementRepository.save(
                            advertisementMapper.advertisementDtoToAdvertisement(advertisement)
                    );
                },
                () -> {
                    throw new EntityNotExistException(advertisement.getId());
                });
    }

    @Override
    public void deleteAdvertisement(final Long id) {
        if (!advertisementRepository.existsById(id))
            throw new EntityNotExistException(id);
        advertisementRepository.deleteById(id);
    }

    @Override
    public void rejectAdvertisement(Long id) {
        updateAdvertisementStatus(id, AdvertisementStatus.REJECTED);
    }

    @Override
    public void confirmAdvertisement(Long id) {
        updateAdvertisementStatus(id, AdvertisementStatus.CONFIRMED);
    }

    private void updateAdvertisementStatus(Long id, AdvertisementStatus status) {
        AdvertisementDto advertisement = getAdvertisement(id);
        advertisement.setStatus(status);
        advertisementRepository.save(advertisementMapper.advertisementDtoToAdvertisement(advertisement));
    }

}
