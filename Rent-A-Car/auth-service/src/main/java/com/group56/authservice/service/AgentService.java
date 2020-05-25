package com.group56.authservice.service;

import com.group56.authservice.DTO.AgentDTO;
import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.model.Agent;
import com.group56.authservice.repository.AgentRepository;
import com.group56.authservice.utility.IdentityCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AgentService {
    private AgentRepository agentRepository;
    private IdentityCheck identityCheck;

    @Autowired
    public AgentService(AgentRepository agentRepository, IdentityCheck identityCheck) {
        this.agentRepository = agentRepository;
        this.identityCheck = identityCheck;
    }

    @Transactional
    public ResponseEntity<?> registerAgent(AgentDTO agentDTO) {
        if(identityCheck.isUsernameUnique(agentDTO.getAgentName())) {
            if(identityCheck.isRegistrationNumberUnique(agentDTO.getRegistrationNumber())) {
                Agent agent = Agent.builder().agentName(agentDTO.getAgentName()).registrationNumber(agentDTO.getRegistrationNumber()).password(agentDTO.getPassword()).address(agentDTO.getAddress()).build();
                agentRepository.save(agent);

                return new ResponseEntity<>("Agent created!", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Registration number is not unique!", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Agent name is not unique!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> loginAgent(LoginDTO loginDTO) {
        Agent agent = agentRepository.findAgentByAgentName(loginDTO.getUsername());
        if(agent.getPassword().equals(loginDTO.getPassword()))
            return new ResponseEntity<>("OK", HttpStatus.OK);
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }
}
