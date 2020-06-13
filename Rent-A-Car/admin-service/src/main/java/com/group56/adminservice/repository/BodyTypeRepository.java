package com.group56.adminservice.repository;

import com.group56.adminservice.model.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {
    boolean existsByBodyType(String bodyType);
    BodyType findByBodyType(String bodyType);
}
