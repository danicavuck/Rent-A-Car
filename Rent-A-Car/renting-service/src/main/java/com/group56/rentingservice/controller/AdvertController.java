package com.group56.rentingservice.controller;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@CrossOrigin
@RequestMapping("/renting-service/advert")
public class AdvertController {

    private AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService aService){
        advertService = aService;
    }

    @GetMapping
    public ResponseEntity<?> getAdvertsForUser(HttpSession session){
        return advertService.getAdvertsForUser(session);
    }
    @GetMapping(value = "/rentRequests")
    public ResponseEntity<?> getRentRequestsForAdverts(Advert advert, HttpSession session){
        return advertService.getRentRequestsForAdvert(advert,session);
    }


}
