package com.group56.authservice.service;

import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.DTO.UserDTO;
import com.group56.authservice.enumeration.Role;
import com.group56.authservice.model.Authorization;
import com.group56.authservice.model.User;
import com.group56.authservice.repository.UserRepository;
import com.group56.authservice.utility.IdentityCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;
    private IdentityCheck identityCheck;

    @Autowired
    public UserService(UserRepository userRepository, IdentityCheck identityCheck) {
        this.userRepository = userRepository;
        this.identityCheck = identityCheck;
    }

    @Transactional
    public ResponseEntity<?> registerNewUser(UserDTO userDTO){
        if(identityCheck.isUsernameUnique(userDTO.getUsername())){
            if(identityCheck.isEmailAddressUnique(userDTO.getEmail())) {
                User user = createUserFromDTO(userDTO);
                userRepository.save(user);

                return new ResponseEntity<>("Account successfully created", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Email address is already in use", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Username is not unique", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> logInUser(LoginDTO loginDTO, HttpSession session) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if(user.getPassword().equals(loginDTO.getPassword())) {
            session.setAttribute("ROLE", Role.USER);
            session.setAttribute("USERNAME", user.getUsername());
            return new ResponseEntity<>("USER", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
    }

    private User createUserFromDTO(UserDTO userDTO) {
        return User.builder().username(userDTO.getUsername()).password(userDTO.getPassword())
                .firstName(userDTO.getFirstName()).lastName(userDTO.getLastName())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail()).build();
    }

}
