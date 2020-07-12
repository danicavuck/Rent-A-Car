package com.group56.rentingservice;

import com.group56.rentingservice.listener.MessageListener;
import com.group56.rentingservice.listener.MessageListenerUser;
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

@SpringBootApplication
@EnableEurekaClient
public class RentingServiceApplication {
	public static final String TOPIC_EXCHANGE_NAME = "advert-exchange-topic";
	public static final String MESSAGE_QUEUE = "advert-renting-update";
	public static final String BINDING = "advert.update.#";

	public static final String TOPIC_EXCHANGE_NAME_USER = "auth-update-exchange";
	public static final String MESSAGE_QUEUE_USER = "renting-update-service";
	public static final String BINDING_USER = "auth.update.#";

	@Bean
	Queue queue() { return new Queue(MESSAGE_QUEUE, false); }

	@Bean
	Queue userQueue() {return new Queue(MESSAGE_QUEUE_USER, false);}

	@Bean
	TopicExchange topicExchange() { return new TopicExchange(TOPIC_EXCHANGE_NAME); }

	@Bean
	TopicExchange userTopic() {return new TopicExchange(TOPIC_EXCHANGE_NAME_USER);}


	@Bean
	Binding binding(Queue queue, TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(BINDING);
	}

	@Bean
	Binding userBinging(Queue userQueue, TopicExchange userTopic) {
		return BindingBuilder.bind(userQueue).to(userTopic).with(BINDING_USER);
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
	SimpleMessageListenerContainer userContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(MESSAGE_QUEUE_USER);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageListener listener) {
		return new MessageListenerAdapter(listener, "listenForMessages");
	}

	@Bean
	MessageListenerAdapter listenerAdapterUser(MessageListenerUser listenerUser) {
		return new MessageListenerAdapter(listenerUser, "listenForMessages");
	}



	public static void main(String[] args) {

		SpringApplication.run(RentingServiceApplication.class, args);
	}


}
