package com.group56.UserService.controller;

import com.group56.UserService.DTO.UserDTO;
import com.group56.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerNewUser(@RequestBody UserDTO userDTO) {
        return  userService.registerNewUser(userDTO);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        return userService.logInUser(userDTO);
    }
}
