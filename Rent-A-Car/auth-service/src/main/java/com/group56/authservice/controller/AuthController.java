package com.group56.authservice.controller;

import com.group56.authservice.DTO.AdminDTO;
import com.group56.authservice.DTO.AgentDTO;
import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.DTO.UserDTO;
import com.group56.authservice.enumeration.Role;
import com.group56.authservice.service.AdminService;
import com.group56.authservice.service.AgentService;
import com.group56.authservice.service.UserService;
import com.group56.authservice.utility.IdentityCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/auth-service")
public class AuthController {
    private UserService userService;
    private AdminService adminService;
    private AgentService agentService;
    private IdentityCheck identityCheck;

    @Autowired
    public AuthController(UserService userService, AdminService adminService, AgentService agentService, IdentityCheck identityCheck) {
        this.userService = userService;
        this.adminService = adminService;
        this.agentService = agentService;
        this.identityCheck = identityCheck;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        Role role = identityCheck.getRoleForUsername(loginDTO.getUsername());
        switch (role){
            case USER: return userService.logInUser(loginDTO);
            case ADMIN: return adminService.logInAdmin(loginDTO);
            case AGENT: return agentService.loginAgent(loginDTO);
            default: return new ResponseEntity<>("Invalid credentials!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/register/user")
    public ResponseEntity<?> registerNewUser(@RequestBody UserDTO userDTO) {
        return  userService.registerNewUser(userDTO);
    }

    @PostMapping(value = "/register/admin")
    public ResponseEntity<?> registerNewAdmin(@RequestBody AdminDTO adminDTO) {
        return adminService.registerNewAdmin(adminDTO);
    }

    @PostMapping(value = "/register/agent")
    public ResponseEntity<?> registerNewAgent(@RequestBody AgentDTO agentDTO) {
        return agentService.registerAgent(agentDTO);
    }
}
