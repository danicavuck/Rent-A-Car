package com.group56.reviewservice.service;

import com.group56.reviewservice.client.SOAPClient;
import com.group56.reviewservice.model.User;
import com.group56.reviewservice.repository.UserRepository;
import com.group56.soap.GetUsersRequest;
import com.group56.soap.GetUsersResponse;
import com.group56.soap.UserXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private SOAPClient soapClient;

    @Autowired
    public UserService(UserRepository userRepository, SOAPClient soapClient) {
        this.userRepository = userRepository;
        this.soapClient = soapClient;
    }



    public void saveUsersToDatabase(List<User> users) {
        users.forEach(user -> {
            userRepository.save(user);
        });
    }

    public List<UserXML> getUsersXMLFromSOAPRequest() {
        GetUsersRequest request = new GetUsersRequest();
        request.setUsername("");
        GetUsersResponse response = soapClient.getUsers(request);
        return response.getUser();
    }
}
