package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    boolean existsByFuelType(String fuelType);
}
