package com.group56.postingservice.listener;

import com.group56.postingservice.model.User;
import com.group56.postingservice.service.UserService;
import com.group56.soap.UserXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private UserService userService;

    @Autowired
    public MessageListener(UserService userService) {
        this.userService = userService;
    }

    public void listenForMessages(byte[] bytes){
        logger.info("message received");
        String event = new String(bytes, StandardCharsets.UTF_8);
        switch (event){
            case "USER_ADDED" : handleUserData();
                break;
            case "USER_MODIFIED" : handleModifiedData();
                break;
            default: logger.error("Unknown data received from auth-service");
        }
    }

    private void handleModifiedData() {
        userService.handleModifiedData();
    }

    private void handleUserData() {
        List<UserXML> userXML = userService.getUsersXMLFromSOAPRequest();
        List<User> users = formUsersFromXML(userXML);
        userService.saveUsersToDatabase(users);
    }

    private List<User> formUsersFromXML(List<UserXML> userXML) {
        List<User> users = new ArrayList<>();

        userXML.forEach(data -> {
            User user = User.builder()
                    .email(data.getEmail())
                    .username(data.getUsername())
                    .firstName(data.getFirstName())
                    .lastName(data.getLastName())
                    .address(data.getAddress())
                    .isActive(true)
                    .isBlocked(false)
                    .build();
            users.add(user);
        });

        return users;
    }
}
