package com.group56.postingservice.service;

import com.group56.postingservice.client.SOAPClient;
import com.group56.postingservice.model.User;
import com.group56.postingservice.repository.UserRepository;
import com.group56.soap.GetUsersRequest;
import com.group56.soap.GetUsersResponse;
import com.group56.soap.UserXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
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
}
