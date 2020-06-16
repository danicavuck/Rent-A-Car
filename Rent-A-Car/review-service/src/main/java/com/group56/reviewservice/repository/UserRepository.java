package com.group56.reviewservice.repository;

import com.group56.reviewservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
