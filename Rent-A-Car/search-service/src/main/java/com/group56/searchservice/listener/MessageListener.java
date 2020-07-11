package com.group56.searchservice.listener;

import com.group56.searchservice.service.AdvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class MessageListener {
    private AdvertService advertService;
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    public MessageListener(AdvertService advertService) {
        this.advertService = advertService;
    }

    public void listenForMessages(byte[] bytes){
        logger.info("listenForMessages()");
        String event = new String(bytes, StandardCharsets.UTF_8);

        switch (event) {
            case "ADVERT_ADDED" : handleAdvertData();
            break;
            default: logger.error("Unknown event occurred in posting service");
        }
    }

    private void handleAdvertData() {
        logger.info("Fetching new Adverts");
        advertService.fetchNewAdverts();
    }
}
