package com.advertisementboard.repository;

import com.advertisementboard.data.entity.Advertisement;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @NonNull Optional<Advertisement> findById(@NonNull Long id);

    @NonNull Page<Advertisement> findAll(@NonNull Pageable pageable);

    Page<Advertisement> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Advertisement> findAllByUserLogin(String userLogin, Pageable pageable);

    Page<Advertisement> findAllByUserLoginAndCategoryId(String userLogin, Long categoryId, Pageable pageable);

}
