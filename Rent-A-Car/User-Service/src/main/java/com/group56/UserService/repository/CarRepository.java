package com.group56.UserService.repository;

import com.group56.UserService.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAll();
}
