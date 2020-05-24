package com.group56.postingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PostingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostingServiceApplication.class, args);
	}

}
