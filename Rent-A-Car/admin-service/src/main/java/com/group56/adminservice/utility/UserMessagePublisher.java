package com.group56.adminservice.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessagePublisher {
    public static final String TOPIC_EXCHANGE_NAME = "auth-update-exchange";
    private RabbitTemplate rabbitTemplate;
    private Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    public UserMessagePublisher(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    public void sendAMessageToQueue(String message) {
        byte[] bytes = message.getBytes();
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "auth.update.user", bytes);
        logger.info("Message has been sent to queue!");
    }
}

