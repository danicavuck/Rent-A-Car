package com.group56.adminservice.service;

import com.group56.adminservice.DTO.UserDTO;
import com.group56.adminservice.model.User;
import com.group56.adminservice.repository.UserRepository;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public ResponseEntity<?> getAllUsers() {
        List users = userRepository.findAll();
        return new ResponseEntity<>(makeDTOFromUsers(users), HttpStatus.OK);
    }

    private List<UserDTO> makeDTOFromUsers(List<User> users) {
        List usersDTO = new ArrayList<>();
        UserDTO userDTO = UserDTO.builder().build();
        users.forEach(user -> {
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
}
