package com.group56.adminservice.repository;

import com.group56.adminservice.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    boolean existsByModelName(String modelName);
    CarModel findByModelName(String modelName);
}
