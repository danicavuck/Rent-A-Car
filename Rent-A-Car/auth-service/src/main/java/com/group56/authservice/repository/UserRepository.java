package com.group56.authservice.repository;

import com.group56.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    boolean existsByUsername(String username);
    boolean existsByRegistrationNumber(String registrationNumber);
    User findByUsername(String username);
}
