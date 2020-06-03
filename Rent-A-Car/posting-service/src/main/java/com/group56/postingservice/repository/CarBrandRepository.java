package com.group56.postingservice.repository;

import com.group56.postingservice.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand,Long> {
    CarBrand findCarBrandById(Long id);
}
