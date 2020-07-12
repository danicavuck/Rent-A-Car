package com.group56.rentingservice.service;

import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.model.User;
import com.group56.rentingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void fetchUsers() {
        RestTemplate restTemplate = new RestTemplate();
        String apiEndpoint = "http://localhost:8080/auth-service/user/rent-service";
        ResponseEntity<User[]> response = restTemplate.getForEntity(apiEndpoint, User[].class);
        User[] users = response.getBody();

        for(int i = 0; i< users.length; i++) {
            userRepository.save(users[i]);
        }
    }
}
