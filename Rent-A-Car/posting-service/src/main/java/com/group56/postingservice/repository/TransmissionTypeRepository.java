package com.group56.postingservice.repository;

import com.group56.postingservice.model.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionTypeRepository extends JpaRepository<TransmissionType , Long> {
    TransmissionType findTransmissionTypeById(Long id);
}
