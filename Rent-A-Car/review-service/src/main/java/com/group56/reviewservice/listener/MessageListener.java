package com.group56.reviewservice.listener;

import com.group56.reviewservice.model.User;
import com.group56.reviewservice.service.UserService;
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

    public void listenForMessages(byte[] bytes) {
        String event = new String(bytes, StandardCharsets.UTF_8);
        switch (event) {
            case "USER_ADDED": handleUserData();
            break;
            default: logger.error("Data received from auth-service is not well formatted");
        }
    }

    private void handleUserData(){
        List<UserXML> userXML = userService.getUsersXMLFromSOAPRequest();
        List<User> users = formUsersFromXML(userXML);
        userService.saveUsersToDatabase(users);
    }

    private List<User> formUsersFromXML(List<UserXML> userXML) {
        List<User> users = new ArrayList<>();

        userXML.forEach(data -> {
            User user = User.builder()
                    .username(data.getUsername())
                    .isActive(data.isIsActive())
                    .isBlocked(data.isIsBlocked())
                    .build();
            users.add(user);
        });

        return users;
    }


}
