package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.util.RentRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface RentRequestRepository extends JpaRepository<RentRequest,Long> {
    ArrayList<RentRequest> findRentRequestByPublisherUsername(String username);
    RentRequest findRentRequestByUuid(String uuid);
    ArrayList<RentRequest> findRentRequestByRequestUsername(String username);

}
