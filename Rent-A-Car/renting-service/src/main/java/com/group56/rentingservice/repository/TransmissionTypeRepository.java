package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Long> {
    boolean existsByTransmissionType(String transmissionType);
}
