package com.group56.UserService.repository;

import com.group56.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
