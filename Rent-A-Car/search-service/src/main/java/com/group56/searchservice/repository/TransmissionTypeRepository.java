package com.group56.searchservice.repository;

import com.group56.searchservice.model.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Long> {
    boolean existsByTransmissionType(String transmissionType);
}
