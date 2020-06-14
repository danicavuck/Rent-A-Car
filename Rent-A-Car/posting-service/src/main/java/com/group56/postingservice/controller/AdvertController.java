package com.group56.postingservice.controller;

import com.group56.adminservice.DTO.CarModelDTO;
import com.group56.postingservice.DTO.AdvertDTO;
import com.group56.postingservice.DTO.AdvertUpdateDTO;
import com.group56.postingservice.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@RequestMapping("/posting-service/advert")
public class AdvertController {
    AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService aService){ this.advertService = aService; }


    @PostMapping
    public ResponseEntity<?> addAdvert(@RequestBody AdvertDTO advertDTO, HttpSession session){
        return advertService.addAdvert(advertDTO, session);
    }

    @PutMapping
    public ResponseEntity<?> updateAdvert(@RequestBody AdvertUpdateDTO advertUpdateDTO, HttpSession session){
        return advertService.updateAdvert(advertUpdateDTO, session);
    }

}
