package com.group56.adminservice.controller;

import com.group56.adminservice.DTO.UserDTO;
import com.group56.adminservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/admin-service/user")
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/block")
    public ResponseEntity<?> blockUser(@RequestBody UserDTO userDTO) {
        return userService.blockUser(userDTO);
    }

    @PostMapping("/active")
    public ResponseEntity<?> activateUser(@RequestBody  UserDTO userDTO) {
        return userService.activeUser(userDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> removeUser(@RequestBody UserDTO userDTO) {
        return userService.removeUser(userDTO);
    }
}
