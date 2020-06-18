package com.group56.authservice.service;

import com.group56.authservice.DTO.AgentDTO;
import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.enumeration.Role;
import com.group56.authservice.model.Agent;
import com.group56.authservice.model.Authorization;
import com.group56.authservice.repository.AgentRepository;
import com.group56.authservice.utility.IdentityCheck;
import com.group56.authservice.utility.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgentService {
    private AgentRepository agentRepository;
    private IdentityCheck identityCheck;
    private MessagePublisher messagePublisher;

    @Autowired
    public AgentService(AgentRepository agentRepository, IdentityCheck identityCheck, MessagePublisher publisher) {
        this.agentRepository = agentRepository;
        this.identityCheck = identityCheck;
        this.messagePublisher = publisher;
    }

    @Transactional
    public ResponseEntity<?> registerAgent(AgentDTO agentDTO) {
        if(identityCheck.isUsernameUnique(agentDTO.getAgentName())) {
            if(identityCheck.isEmailAddressUnique(agentDTO.getEmail())) {
                if(identityCheck.isRegistrationNumberUnique(agentDTO.getRegistrationNumber())) {
                    Agent agent = createAgentFromDTO(agentDTO);
                    agentRepository.save(agent);

                    messagePublisher.sendAMessageToQueue("AGENT_ADDED");
                    return new ResponseEntity<>("Account successfully created", HttpStatus.CREATED);
                }
                return new ResponseEntity<>("Registration number is not unique!", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Email address is already in use", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Agent name is not unique!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> loginAgent(LoginDTO loginDTO, HttpSession session) {
        Agent agent = agentRepository.findAgentByAgentName(loginDTO.getUsername());
        if(agent.getPassword().equals(loginDTO.getPassword())){
            session.setAttribute("ROLE", Role.AGENT);
            session.setAttribute("USERNAME", agent.getAgentName());
            return new ResponseEntity<>("AGENT", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    private Agent createAgentFromDTO(AgentDTO agentDTO) {
        return Agent.builder().agentName(agentDTO.getAgentName())
                .registrationNumber(agentDTO.getRegistrationNumber())
                .password(agentDTO.getPassword())
                .address(agentDTO.getAddress())
                .isSharedWithAdmin(false)
                .email(agentDTO.getEmail()).build();
    }

    public List<Agent> getAgentsThatAreNotSharedWithAdminService(String username) {
        List<Agent> allAgents = agentRepository.findAll();
        List<Agent> relevantAgents = new ArrayList<>();

        allAgents.forEach(agent -> {
            if(!agent.isSharedWithAdmin()) {
                relevantAgents.add(agent);
                agent.setSharedWithAdmin(true);
                agentRepository.save(agent);
            }
        });

        return relevantAgents;
    }
}
