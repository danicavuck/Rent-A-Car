package com.group56.rentingservice.controller;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.service.RentRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController@Controller
@CrossOrigin
@RequestMapping("/renting-service/rentRequest")
public class RentRequestController {

    private RentRequestService rentRequestService;

    public RentRequestController(RentRequestService rrService){
        this.rentRequestService = rrService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addRentRequest(@RequestBody RentRequestDTO rentRequestDTO, HttpSession session){
        return rentRequestService.addRentRequest(rentRequestDTO, session);
    }


}
