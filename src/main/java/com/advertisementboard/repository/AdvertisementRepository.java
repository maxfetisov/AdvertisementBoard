package com.advertisementboard.repository;

import com.advertisementboard.data.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Optional<Advertisement> findById(Long id);

    Page<Advertisement> findAll(Pageable pageable);

    Page<Advertisement> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Advertisement> findAllByUserLogin(String userLogin, Pageable pageable);

    Page<Advertisement> findAllByUserLoginAndAndCategoryId(String userLogin, Long categoryId, Pageable pageable);

}
