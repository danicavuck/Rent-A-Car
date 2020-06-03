package com.group56.postingservice.repository;

import com.group56.postingservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    public Car findAllBy();

}
