package com.group56.adminservice.repository;

import com.group56.adminservice.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    boolean existsByBrandName(String brandName);
    CarBrand findByBrandName(String brandName);
}
