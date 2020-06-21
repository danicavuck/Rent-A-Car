package com.group56.adminservice.controller;

import com.group56.adminservice.service.SaveTestDataToDatabase;
import com.group56.adminservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin-service/test")
public class TestController {
    private SaveTestDataToDatabase saveTestDataToDatabase;
    private UserService userService;

    @Autowired
    public TestController(SaveTestDataToDatabase saveTestDataToDatabase, UserService userService) {
        this.saveTestDataToDatabase = saveTestDataToDatabase;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> saveData() {
        saveTestDataToDatabase.saveData();
        userService.test();
        return new ResponseEntity<>("Data saved", HttpStatus.OK);
    }
}
