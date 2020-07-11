package com.group56.searchservice.repository;

import com.group56.searchservice.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    boolean existsByFuelType(String fuelType);
}
