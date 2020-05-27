package com.group56.adminservice.repository;

import com.group56.adminservice.model.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Long> {
    boolean existsByTransmissionType(String transmissionType);
    TransmissionType findByTransmissionType(String transmissionType);
}
