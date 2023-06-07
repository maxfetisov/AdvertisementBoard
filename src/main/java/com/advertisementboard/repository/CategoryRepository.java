package com.advertisementboard.repository;

import com.advertisementboard.data.entity.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    void deleteById(@NonNull Long id);

    @NonNull Optional<Category> findById(@NonNull Long id);

    boolean existsByName(String name);

}
