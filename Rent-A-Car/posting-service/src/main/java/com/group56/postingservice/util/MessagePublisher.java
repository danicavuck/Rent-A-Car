package com.group56.postingservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
    private RabbitTemplate rabbitTemplate;
    private Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    public MessagePublisher(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    public void sendAMessageToQueue(String message) {
        byte[] bytes = message.getBytes();
        rabbitTemplate.convertAndSend("advert-update", bytes);
        logger.info("Message has been sent to queue!");
    }
}
