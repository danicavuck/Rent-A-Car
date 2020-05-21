package com.group56.UserService.repository;

import com.group56.UserService.model.RentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {
    List<RentRequest> findAll();
}
