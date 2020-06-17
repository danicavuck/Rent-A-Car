package com.group56.postingservice.controller;

import com.group56.postingservice.DTO.AdvertDTO;
import com.group56.postingservice.DTO.AdvertUpdateDTO;
import com.group56.postingservice.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/posting-service/advert")
public class AdvertController {

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

    @PostMapping("/images")
    public ResponseEntity<?> saveAdvertImages() {
        return new ResponseEntity<>("201", HttpStatus.CREATED);
    }

}
