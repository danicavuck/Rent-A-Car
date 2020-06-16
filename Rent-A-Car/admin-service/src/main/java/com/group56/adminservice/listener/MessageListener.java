package com.group56.adminservice.listener;

import com.group56.adminservice.model.Admin;
import com.group56.adminservice.model.Agent;
import com.group56.adminservice.model.User;
import com.group56.adminservice.service.AdminService;
import com.group56.adminservice.service.AgentService;
import com.group56.adminservice.service.UserService;
import com.group56.soap.AdminXML;
import com.group56.soap.AgentXML;
import com.group56.soap.UserXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageListener {
    private UserService userService;
    private AdminService adminService;
    private AgentService agentService;
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    public MessageListener(UserService userService, AdminService adminService, AgentService agentService) {
        this.userService = userService;
        this.adminService = adminService;
        this.agentService = agentService;
    }

    public void listenForMessages(byte[] bytes){
        String event = new String(bytes, StandardCharsets.UTF_8);
        switch (event){
            case "USER_ADDED" : handleUserData();
            break;
            case "ADMIN_ADDED" : //handleAdminData();
            break;
            case "AGENT_ADDED" : //handleAgentData();
            break;
            default: logger.error("Unknown data received from auth-service");
        }
    }

    private void handleUserData() {
        List<UserXML> userXML = userService.getUsersXMLFromSOAPRequest();
        List<User> users = formUsersFromXML(userXML);
        userService.saveUsersToDatabase(users);
    }

    private void handleAdminData(){
        List<AdminXML> adminXML = adminService.getUsersXMLFromSOAPRequest();
        List<Admin> admins = formAdminsFromXML(adminXML);
        adminService.saveAdminsToDatabase(admins);

    }

    private void handleAgentData() {
        List<AgentXML> agentsXML = agentService.getAgentsXMLFromSOAPRequest();
        List<Agent> agents = formAgentsFromXML(agentsXML);
        agentService.saveAgentsToDatabase(agents);
    }

    private List<User> formUsersFromXML(List<UserXML> usersXML) {
        List<User> users = new ArrayList<>();

        usersXML.forEach(data -> {
            User user = User.builder()
                    .firstName(data.getFirstName()).lastName(data.getLastName())
                    .email(data.getEmail()).address(data.getAddress())
                    .isActive(data.isIsActive())
                    .username(data.getUsername())
                    .build();
            users.add(user);
        });

        return users;
    }

    private List<Admin> formAdminsFromXML(List<AdminXML> adminsXML) {
        List<Admin> admins = new ArrayList<>();

        adminsXML.forEach(data -> {
            Admin admin = Admin.builder()
                    .address(data.getAddress())
                    .adminName(data.getAdminName())
                    .firstName(data.getFirstName())
                    .lastName(data.getLastName())
                    .email(data.getEmail())
                    .build();

            admins.add(admin);
        });

        return admins;
    }

    private List<Agent> formAgentsFromXML(List<AgentXML> agentsXML) {
        List<Agent> agents = new ArrayList<>();

        agentsXML.forEach(data -> {
            Agent agent = Agent.builder()
                    .agentName(data.getAgentName())
                    .email(data.getEmail())
                    .address(data.getAddress())
                    .registrationNumber(data.getRegistrationNumber())
                    .build();

            agents.add(agent);
        });

        return agents;
    }


}
