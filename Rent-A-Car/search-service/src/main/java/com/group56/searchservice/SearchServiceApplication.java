package com.group56.searchservice;

import com.group56.searchservice.listener.MessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class SearchServiceApplication {
	public static final String TOPIC_EXCHANGE_NAME = "advert-exchange-topic";
	public static final String MESSAGE_QUEUE = "advert-search-update";
	public static final String BINDING = "advert.update.#";

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
		SpringApplication.run(SearchServiceApplication.class, args);
	}

}
