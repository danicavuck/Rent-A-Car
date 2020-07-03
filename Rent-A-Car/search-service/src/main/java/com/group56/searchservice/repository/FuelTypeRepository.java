package com.group56.searchservice.repository;

import com.group56.searchservice.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    boolean existsByFuelType(String fuelType);
}
