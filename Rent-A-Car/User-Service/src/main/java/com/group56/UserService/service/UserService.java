package com.group56.UserService.service;

import com.group56.UserService.DTO.UserDTO;
import com.group56.UserService.model.User;
import com.group56.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> registerNewUser(UserDTO userDTO){
        if(!userRepository.existsByUsername(userDTO.getUsername())){
            if(!userRepository.existsByRegistrationNumber(userDTO.getRegistrationNumber())){
                User user = User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).firstName(userDTO.getFirstName()).lastName(userDTO.getLastName()).address(userDTO.getAddress()).registrationNumber(userDTO.getRegistrationNumber()).build();
                userRepository.save(user);
                return new ResponseEntity<>("User registered", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Registration number is not unique", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Username is not unique", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> logInUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())){
            User user = userRepository.findByUsername(userDTO.getUsername());
            if(user.getPassword().equals(userDTO.getPassword())) {
                return new ResponseEntity<>("Logged in", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
    }

}
