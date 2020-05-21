package com.group56.UserService.repository;

import com.group56.UserService.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AgentRepository extends JpaRepository<Agent, Long> {
    List<Agent> findAll();
}
