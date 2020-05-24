package com.group56.authservice.utility;

import com.group56.authservice.enumeration.Role;
import com.group56.authservice.repository.AdminRepository;
import com.group56.authservice.repository.AgentRepository;
import com.group56.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentityCheck {
    private AdminRepository adminRepository;
    private AgentRepository agentRepository;
    private UserRepository userRepository;

    @Autowired
    public IdentityCheck(AdminRepository adminRepository, AgentRepository agentRepository, UserRepository userRepository){
        this.adminRepository = adminRepository;
        this.agentRepository = agentRepository;
        this.userRepository = userRepository;
    }

    public Role getRoleForUsername(String username) {
        if(adminRepository.existsByAdminName(username))
            return Role.ADMIN;
        if(agentRepository.existsByAgentName(username))
            return Role.AGENT;
        if(userRepository.existsByUsername(username))
            return Role.USER;
        return null;
    }

    public boolean isUsernameUnique(String username){
        return !adminRepository.existsByAdminName(username) && !agentRepository.existsByAgentName(username) && !userRepository.existsByUsername(username);
    }

    public boolean isRegistrationNumberUnique(String registration) {
        return !adminRepository.existsByRegistrationNumber(registration) && !agentRepository.existsByRegistrationNumber(registration) && !userRepository.existsByRegistrationNumber(registration);
    }
}
