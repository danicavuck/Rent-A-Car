package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    boolean existsByModelName(String modelName);
}
