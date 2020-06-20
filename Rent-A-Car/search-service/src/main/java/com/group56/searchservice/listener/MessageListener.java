package com.group56.searchservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    public void listenForMessages(byte[] bytes){
        logger.info("listenForMessages()");
        String event = new String(bytes, StandardCharsets.UTF_8);

        switch (event) {
            case "ADVERT_ADDED" : logger.info("Advert is added");
            break;
            default: logger.error("Unknown event occurred in posting service");
        }
    }
}
