package com.group56.notificationservice.repository;

import com.group56.notificationservice.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
