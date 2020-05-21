package com.group56.UserService.service;

import com.group56.UserService.DTO.UserDTO;
import com.group56.UserService.model.User;
import com.group56.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerNewUser(UserDTO userDTO){
        if(!userRepository.existsByUsername(userDTO.getUsername())){
            User user = User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).build();
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
