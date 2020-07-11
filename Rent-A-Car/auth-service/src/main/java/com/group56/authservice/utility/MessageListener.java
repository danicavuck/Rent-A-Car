package com.group56.authservice.utility;

import com.group56.authservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private UserService userService;

    @Autowired
    public MessageListener(UserService userService) {
        this.userService = userService;
    }

    public void listenForMessages(byte[] bytes){
        String event = new String(bytes, StandardCharsets.UTF_8);
        switch (event){
            case "USER_MODIFIED" : handleUserData();
                break;
            default: logger.error("Unknown data received from auth-service");
        }
    }

    private void handleUserData() {
        logger.info("Fetching Users");
        userService.fetchUsers();
    }
}
