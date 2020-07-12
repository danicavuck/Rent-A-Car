package com.group56.rentingservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListenerUser {
    private Logger logger = LoggerFactory.getLogger(MessageListenerUser.class);

    @Autowired
    public MessageListenerUser(){
    }

    public void listenForMessages(byte[] bytes){
        logger.info("User notified");
//        String event = new String(bytes, StandardCharsets.UTF_8);
//        switch (event){
//            case "USER_MODIFIED" : handleUserData();
//                break;
//            default: logger.error("Unknown data received from auth-service");
//        }
    }

//    private void handleUserData() {
//        logger.info("Fetching Users");
//        userService.fetchUsers();
//    }
}
