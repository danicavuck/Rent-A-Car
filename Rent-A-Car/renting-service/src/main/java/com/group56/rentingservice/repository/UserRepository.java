package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserById(Long id);
    User findUserByUsername(String username);
}
