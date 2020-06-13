package com.group56.authservice.repository;

import com.group56.authservice.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByAdminName(String adminName);
    boolean existsByAdminName(String adminName);
    boolean existsByEmail(String email);
}
