package com.group56.postingservice.controller;

import com.group56.postingservice.DTO.AdvertDTO;
import com.group56.postingservice.DTO.AdvertUpdateDTO;
import com.group56.postingservice.DTO.RentRequestDTO;
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
import java.util.ArrayList;
import java.util.Base64;

@Controller
@RequestMapping("/posting-service/advert")
public class AdvertController {
    private Logger logger = LoggerFactory.getLogger(AdvertController.class);
    public static final String uploadDirectory = System.getProperty("user.dir") + "/images";
    private AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService aService){ this.advertService = aService; }

    @GetMapping
    public ResponseEntity<?> getAdverts() {
        return advertService.getAdverts();
    }

    @PostMapping
    public ResponseEntity<?> addAdvert(@RequestBody AdvertDTO advertDTO){
        return advertService.addAdvert(advertDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateAdvert(@RequestBody AdvertUpdateDTO advertUpdateDTO){
        return advertService.updateAdvert(advertUpdateDTO);
    }

    @PostMapping("/fromRentRequest")
    public ResponseEntity<?> getAdvertFromRentRequest(@RequestBody RentRequestDTO rentRequestDTO){
        return advertService.getAdvertsFromRentRequest(rentRequestDTO);
    }

    @GetMapping("/{advertUUID}")
    public ResponseEntity<?> getSingleAdvert(@PathVariable("advertUUID") String uuid) {
        return advertService.getSingleAdvert(uuid);
    }

    @PostMapping("/advertList")
    public ResponseEntity<?> getListAdverts(@RequestBody ArrayList<String> uuid) {
        return advertService.getListAdverts(uuid);
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

    @GetMapping("/profile-image/{advertUUID}")
    public ResponseEntity<?> getProfilePicture(@PathVariable("advertUUID") String uuid) {
        File rootFile = new File(uploadDirectory);
        if (rootFile != null) {
            for (File file : rootFile.listFiles()) {
                if (file.getName().equals(uuid)) {
                    return new ResponseEntity<>(file, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Profile picture not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Could not find folder", HttpStatus.NOT_FOUND);
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
