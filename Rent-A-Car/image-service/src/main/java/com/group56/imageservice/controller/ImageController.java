package com.group56.imageservice.controller;

import com.group56.imageservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/image-service")
public class ImageController {
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/profile-image/{advertUUID}")
    public ResponseEntity<?> postAProfilePhoto(Model model,
                                               @RequestParam("images") MultipartFile[] images,
                                               @PathVariable("advertUUID") String uuid) {

        return imageService.postAProfilePhoto(model, images, uuid);
    }

    @GetMapping("/profile-image/{advertUUID}")
    public ResponseEntity<?> getAProfilePhoto(@PathVariable("advertUUID") String uuid) {
        return imageService.getAProfilePhoto(uuid);
    }
}
