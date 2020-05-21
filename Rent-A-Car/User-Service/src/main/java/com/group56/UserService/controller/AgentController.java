package com.group56.UserService.controller;

import com.group56.UserService.DTO.AgentDTO;
import com.group56.UserService.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/agents")
public class AgentController {
    private AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerNewAgent(@RequestBody AgentDTO agentDTO) {
        return agentService.registerNewAgent(agentDTO);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginAgent(@RequestBody AgentDTO agentDTO) {
        return agentService.logInAgent(agentDTO);
    }
}
