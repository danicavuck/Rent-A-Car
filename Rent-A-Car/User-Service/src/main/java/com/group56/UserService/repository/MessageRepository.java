package com.group56.UserService.repository;

import com.group56.UserService.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAll();
}
