package com.group56.authservice.service;

import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.DTO.UserDTO;
import com.group56.authservice.model.User;
import com.group56.authservice.repository.UserRepository;
import com.group56.authservice.utility.IdentityCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private IdentityCheck identityCheck;

    @Autowired
    public UserService(UserRepository userRepository, IdentityCheck identityCheck) {
        this.userRepository = userRepository;
        this.identityCheck = identityCheck;
    }

    public ResponseEntity<?> registerNewUser(UserDTO userDTO){
        if(identityCheck.isUsernameUnique(userDTO.getUsername())){
            if(identityCheck.isRegistrationNumberUnique(userDTO.getRegistrationNumber())){
                User user = User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).firstName(userDTO.getFirstName()).lastName(userDTO.getLastName()).address(userDTO.getAddress()).registrationNumber(userDTO.getRegistrationNumber()).build();
                userRepository.save(user);
                return new ResponseEntity<>("User registered", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Registration number is not unique", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Username is not unique", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> logInUser(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if(user.getPassword().equals(loginDTO.getPassword()))
            return new ResponseEntity<>("Logged in", HttpStatus.OK);
        return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
    }

}
