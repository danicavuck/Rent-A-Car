package com.group56.rentingservice;

import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.model.User;
import com.group56.rentingservice.repository.AdvertRepository;
import com.group56.rentingservice.repository.RentRequestRepository;
import com.group56.rentingservice.repository.UserRepository;
import com.group56.rentingservice.util.RentRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
public class RentingServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(RentingServiceApplication.class, args);
	}


}
