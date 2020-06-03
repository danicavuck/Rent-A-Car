package com.group56.postingservice.repository;

import com.group56.postingservice.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel,Long> {
    CarModel findCarModelById(Long id);
}
