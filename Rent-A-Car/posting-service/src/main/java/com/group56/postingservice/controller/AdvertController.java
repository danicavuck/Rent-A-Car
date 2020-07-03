package com.group56.postingservice.controller;

import com.group56.postingservice.DTO.AdvertDTO;
import com.group56.postingservice.DTO.AdvertUpdateDTO;
import com.group56.postingservice.service.AdvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
@RequestMapping("/posting-service/advert")
public class AdvertController {
    private Logger logger = LoggerFactory.getLogger(AdvertController.class);
    public static final String uploadDirectory = System.getProperty("user.dir") + "/images";

    private AdvertService advertService;

    @GetMapping
    public ResponseEntity<?> getAdverts() {
        return advertService.getAdverts();
    }

    @Autowired
    public AdvertController(AdvertService aService){ this.advertService = aService; }


    @PostMapping
    public ResponseEntity<?> addAdvert(@RequestBody AdvertDTO advertDTO){
        return advertService.addAdvert(advertDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateAdvert(@RequestBody AdvertUpdateDTO advertUpdateDTO){
        return advertService.updateAdvert(advertUpdateDTO);
    }

    @GetMapping("/{advertUUID}")
    public ResponseEntity<?> getSingleAdvert(@PathVariable("advertUUID") String uuid) {
        return advertService.getSingleAdvert(uuid);
    }

    @GetMapping("/search-service")
    public ResponseEntity<?> getAdvertsForSearchService() {
        return advertService.getAdvertsForSearchService();
    }


    @GetMapping("/test")
    public ResponseEntity<?> testing() {
        return advertService.testing();
    }

}
