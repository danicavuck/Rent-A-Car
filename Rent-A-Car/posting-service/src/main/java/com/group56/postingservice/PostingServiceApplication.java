package com.group56.postingservice;

import com.group56.postingservice.controller.AdvertController;
import com.group56.postingservice.listener.MessageListener;
import com.group56.postingservice.model.*;
import com.group56.postingservice.repository.AdvertRepository;
import com.sun.deploy.security.CertStore;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.group56.postingservice", "controller"})
public class PostingServiceApplication {

	public static final String TOPIC_EXCHANGE_NAME = "auth-update-exchange";
	public static final String MESSAGE_QUEUE = "auth-update-posting-service";
	public static final String BINDING = "auth.update.#";

	@Bean
	Queue queue() { return new Queue(MESSAGE_QUEUE, false); }

	@Bean
	TopicExchange topicExchange() { return new TopicExchange(TOPIC_EXCHANGE_NAME); }

	@Bean
	Binding binding(Queue queue, Queue anotherQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(BINDING);
	}


	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(MESSAGE_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageListener listener) {
		return new MessageListenerAdapter(listener, "listenForMessages");
	}

	public static void main(String[] args) {
		new File(AdvertController.uploadDirectory).mkdir();

		SpringApplication.run(PostingServiceApplication.class, args);
	}


}
