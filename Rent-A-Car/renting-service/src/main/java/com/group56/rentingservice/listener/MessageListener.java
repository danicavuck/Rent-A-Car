package com.group56.rentingservice.listener;

import com.group56.rentingservice.service.AdvertService;
import com.group56.rentingservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {
    private AdvertService advertService;
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    public MessageListener(AdvertService advertService, UserService userService) {
        this.advertService = advertService;
        this.userService = userService;
    }

    public void listenForMessages(byte[] bytes){
        logger.info("listenForMessages()");
        String event = new String(bytes, StandardCharsets.UTF_8);

        switch (event) {
            case "ADVERT_ADDED" : handleAdvertData();
                break;
            case "USER_ADDED" : handleUserData();
            break;
            default: logger.error("Unknown event occurred in posting service");
        }
    }

    private void handleAdvertData() {
        logger.info("Fetching new Adverts");
        advertService.fetchNewAdverts();
    }

    private void handleUserData() {
        logger.info("Fetching users");
        userService.fetchUsers();
    }
}
