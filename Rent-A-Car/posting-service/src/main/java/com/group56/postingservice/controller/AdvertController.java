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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/posting-service/advert")
public class AdvertController {
    private Logger logger = LoggerFactory.getLogger(AdvertController.class);
    public static final String uploadDirectory = System.getProperty("user.dir") + "/images";

    private AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService aService){ this.advertService = aService; }


    @PostMapping
    public ResponseEntity<?> addAdvert(@RequestBody AdvertDTO advertDTO){
        return advertService.addAdvert(advertDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateAdvert(@RequestBody AdvertUpdateDTO advertUpdateDTO, HttpSession session){
        return advertService.updateAdvert(advertUpdateDTO, session);
    }

    @PostMapping("/profile-image/{advertUUID}")
    public ResponseEntity<?> saveAdvertImages(Model model,
                                              @RequestParam("images") MultipartFile[] images,
                                              @PathVariable("advertUUID") String uuid) {
        StringBuilder imageNames = new StringBuilder();
        logger.info("Image saved at location: " + uploadDirectory);
        for(MultipartFile image : images) {
            //Path path = Paths.get(uploadDirectory, image.getOriginalFilename());
            Path path = Paths.get(uploadDirectory, uuid);
            imageNames.append(image.getOriginalFilename());
            try {
                Files.write(path, image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Unable to save image to specified path");
                return new ResponseEntity<>("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Image successfully saved", HttpStatus.CREATED);
    }

}
