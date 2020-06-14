package com.group56.adminservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    public void listenForMessages(byte[] bytes){
        logger.info("Event triggered from Auth Service: " + new String(bytes, StandardCharsets.UTF_8));
    }

}
