package com.group56.searchservice.repository;

import com.group56.searchservice.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    boolean existsByBrandName(String brandName);
}
