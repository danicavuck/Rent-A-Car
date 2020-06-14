package com.group56.searchservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    public void listenForMessages(byte[] bytes){
        logger.info("Message from queue1: " + new String(bytes, StandardCharsets.UTF_8));
    }

    public void listenForDeleteMessage(byte[] bytes) {
        logger.info("Message from queue2: " + new String(bytes, StandardCharsets.UTF_8));
    }

}
