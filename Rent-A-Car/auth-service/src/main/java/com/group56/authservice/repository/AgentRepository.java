package com.group56.authservice.repository;

import com.group56.authservice.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Agent findAgentByAgentName(String agentName);
    boolean existsByEmail(String email);
    boolean existsByAgentName(String agentName);
    boolean existsByRegistrationNumber(String registrationNumber);
}
