package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {
    boolean existsByBodyType(String bodyType);
}
