package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.RentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRequestRepository extends JpaRepository<RentRequest,Long> {
}
