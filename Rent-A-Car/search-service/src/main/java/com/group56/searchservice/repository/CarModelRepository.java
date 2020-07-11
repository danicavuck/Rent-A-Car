package com.group56.searchservice.repository;

import com.group56.searchservice.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    boolean existsByModelName(String modelName);
}
