package com.group56.postingservice.repository;

import com.group56.postingservice.model.RentRequest;
import com.group56.postingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface RentRequestRepository extends JpaRepository<RentRequest,Long> {
    RentRequest findRentRequestById(Long id);

    RentRequest findRentRequestByUuid(UUID uuid);

}
