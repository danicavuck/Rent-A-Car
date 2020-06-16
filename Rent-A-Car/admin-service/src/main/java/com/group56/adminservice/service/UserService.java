package com.group56.adminservice.service;

import com.group56.adminservice.DTO.UserDTO;
import com.group56.adminservice.client.SoapClient;
import com.group56.adminservice.model.User;
import com.group56.adminservice.repository.UserRepository;
import com.group56.soap.GetUsersRequest;
import com.group56.soap.GetUsersResponse;
import com.group56.soap.UserXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private SoapClient soapClient;

    @Autowired
    public UserService(UserRepository userRepository, SoapClient soapClient) {
        this.userRepository = userRepository;
        this.soapClient = soapClient;
    }

    @Transactional
    public ResponseEntity<?> blockUser(UserDTO userDTO) {
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        if(user != null) {
            if(user.isActive()){
                user.setBlocked(true);
                userRepository.save(user);
                return new ResponseEntity<>("User is blocked!", HttpStatus.OK);
            }
            return new ResponseEntity<>("User is already deleted!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Couldn't find user with provided username", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> activeUser(UserDTO userDTO){
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        if(user != null) {
            if(user.isActive()){
                user.setBlocked(false);
                userRepository.save(user);
                return new ResponseEntity<>("User is activated!", HttpStatus.OK);
            }
            return new ResponseEntity<>("User deleted!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Couldn't find user with provided username", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> removeUser(UserDTO userDTO) {
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        if(user != null) {
            if(user.isActive()){
                user.setActive(false);
                userRepository.save(user);
                return new ResponseEntity<>("User is successfully deleted!", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("User is already deleted!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Couldn't find user with provided username", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllActiveUsers() {
        List<User> users = userRepository.findAll();
        List activeUsers = new ArrayList();
        users.forEach(user -> {
            if(user.isActive())
                activeUsers.add(user);
        });
        return new ResponseEntity<>(makeDTOFromUsers(activeUsers), HttpStatus.OK);
    }

    public List<UserXML> getUsersXMLFromSOAPRequest() {
        GetUsersRequest request = new GetUsersRequest();
        request.setUsername("");
        GetUsersResponse response = soapClient.getUsers(request);
        return response.getUser();
    }

    private List<UserDTO> makeDTOFromUsers(List<User> users) {
        List usersDTO = new ArrayList<>();
        users.forEach(user -> {
            UserDTO userDTO = UserDTO.builder().build();
            userDTO.setUsername(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setAddress(user.getAddress());
            userDTO.setEmail(user.getEmail());
            userDTO.setNumberOfAdvertsCancelled(user.getNumberOfAdvertsCancelled());

            usersDTO.add(userDTO);
        });

        return usersDTO;
    }

    public void saveUsersToDatabase(List<User> users) {
        users.forEach(user -> {
            userRepository.save(user);
        });
    }
}
