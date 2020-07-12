package com.group56.postingservice.service;

import com.group56.postingservice.DTO.UserDetailsDTO;
import com.group56.postingservice.client.SOAPClient;
import com.group56.postingservice.model.User;
import com.group56.postingservice.repository.UserRepository;
import com.group56.soap.GetUsersRequest;
import com.group56.soap.GetUsersResponse;
import com.group56.soap.UserXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private SOAPClient soapClient;

    @Autowired
    public UserService(UserRepository userRepository, SOAPClient client) {
        this.userRepository = userRepository;
        this.soapClient = client;
    }

    public List<UserXML> getUsersXMLFromSOAPRequest() {
        GetUsersRequest request = new GetUsersRequest();
        request.setUsername("");
        GetUsersResponse response = soapClient.getUsers(request);
        return response.getUser();
    }

    public void saveUsersToDatabase(List<User> users) {
        users.forEach(user -> {
            userRepository.save(user);
        });
    }

    public ResponseEntity getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if(user == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        UserDetailsDTO userDTO = formADtoFromUser(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    private UserDetailsDTO formADtoFromUser(User user) {
        return UserDetailsDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .numberOfAdvertsCancelled(user.getNumberOfAdvertsCancelled())
                .build();
    }

    public void handleModifiedData() {
        RestTemplate restTemplate = new RestTemplate();
        String apiEndpoint = "http://localhost:8080/admin-service/user/modified";
        ResponseEntity<User[]> response = restTemplate.getForEntity(apiEndpoint, User[].class);
        User[] users = response.getBody();

        for(int i = 0; i<users.length; i++) {
            modifyAndSaveUser(users[i]);
        }
    }

    private void modifyAndSaveUser(User modifiedUser) {
        User user = userRepository.findUserByUsername(modifiedUser.getUsername());
        if(user != null) {
            user.setActive(modifiedUser.isActive());
            user.setBlocked(modifiedUser.isBlocked());
            userRepository.save(user);
        }
    }
}
