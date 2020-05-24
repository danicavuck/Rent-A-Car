package com.group56.rentingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RentingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentingServiceApplication.class, args);
	}

}
