package com.group56.adminservice.controller;

import com.group56.adminservice.service.SaveTestDataToDatabase;
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

    @Autowired
    public TestController(SaveTestDataToDatabase saveTestDataToDatabase) {
        this.saveTestDataToDatabase = saveTestDataToDatabase;
    }
    @GetMapping
    public ResponseEntity<?> saveData() {
        saveTestDataToDatabase.saveData();
        return new ResponseEntity<>("Data saved", HttpStatus.OK);
    }
}
