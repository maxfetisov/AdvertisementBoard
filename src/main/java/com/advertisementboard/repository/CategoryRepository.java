package com.advertisementboard.repository;

import com.advertisementboard.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    void deleteById(Long id);

    Optional<Category> findById(Long id);

}
