package com.group56.adminservice.repository;

import com.group56.adminservice.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    boolean existsByFuelType(String fuelType);
    FuelType findByFuelType(String fuelType);
}
