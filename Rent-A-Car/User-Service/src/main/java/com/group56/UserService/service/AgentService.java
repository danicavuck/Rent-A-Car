package com.group56.UserService.service;

import com.group56.UserService.DTO.AgentDTO;
import com.group56.UserService.model.Agent;
import com.group56.UserService.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
    private AgentRepository agentRepository;

    @Autowired
    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public ResponseEntity<?> registerNewAgent(AgentDTO agentDTO) {
        if(!agentRepository.existsByRegistrationNumber(agentDTO.getRegistrationNumber())){
            if(!agentRepository.existsByAgentName(agentDTO.getAgentName())){
                Agent agent = Agent.builder().agentName(agentDTO.getAgentName()).password(agentDTO.getPassword()).address(agentDTO.getAddress()).registrationNumber(agentDTO.getRegistrationNumber()).build();
                agentRepository.save(agent);
                return new ResponseEntity<>("Agent successfully saved", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Agent name already exists", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Registration number is not unique", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> logInAgent(AgentDTO agentDTO) {
        if(agentRepository.existsByAgentName(agentDTO.getAgentName())) {
            Agent agent = agentRepository.findByAgentName(agentDTO.getAgentName());
            if(agent.getPassword().equals(agentDTO.getPassword())) {
                return new ResponseEntity<>("Successfully logged in", HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Agent not found", HttpStatus.NOT_FOUND);
    }
}
