package com.group56.postingservice;

import com.group56.postingservice.controller.AdvertController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.group56.postingservice", "controller"})
public class PostingServiceApplication {
	public static void main(String[] args) {
		new File(AdvertController.uploadDirectory).mkdir();
		SpringApplication.run(PostingServiceApplication.class, args);
	}

}
