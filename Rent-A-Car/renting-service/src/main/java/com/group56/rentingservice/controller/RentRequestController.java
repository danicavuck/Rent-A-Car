package com.group56.rentingservice.controller;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.service.RentRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/renting-service/rentRequest")
public class RentRequestController {

    private RentRequestService rentRequestService;

    public RentRequestController(RentRequestService rrService){
        this.rentRequestService = rrService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addRentRequest(@RequestBody RentRequestDTO rentRequestDTO){
        System.out.println(rentRequestDTO.getAdvertIds());
        System.out.println(rentRequestDTO.getUsername());
        return rentRequestService.addRentRequest(rentRequestDTO);
    }

    @GetMapping(value = "/owner")
    public ResponseEntity<?> getRentRequestsForOwner(String username){
        return rentRequestService.getRentRequestsForOwner(username);
    }


    @PutMapping(value = "/accept")
    public ResponseEntity<?> acceptRentRequest(Long rrId,String username){
        return rentRequestService.acceptRentRequest(rrId,username);
    }


    @PutMapping(value = "/decline")
    public ResponseEntity<?> declineRentRequest(Long rrId,String username){
        return rentRequestService.declineRentRequest(rrId,username);
    }

}
