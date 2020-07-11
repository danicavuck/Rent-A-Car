package com.group56.rentingservice.controller;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.service.RentRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/renting-service/rentRequest")
public class RentRequestController {

    private RentRequestService rentRequestService;

    public RentRequestController(RentRequestService rrService){
        this.rentRequestService = rrService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addRentRequest(@RequestBody RentRequestDTO rentRequestDTO){
        return rentRequestService.addRentRequest(rentRequestDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getRentRequestsForOwner(@PathVariable("username") String user){
        return rentRequestService.getRentRequestsForOwner(user);
    }


    @PostMapping(value = "/accept/{username}")
    public ResponseEntity<?> acceptRentRequest(@PathVariable("username") String username,@RequestBody String rrId){
        return rentRequestService.acceptRentRequest(username,rrId);
    }


    @PostMapping(value = "/decline/{username}")
    public ResponseEntity<?> declineRentRequest(@PathVariable("username") String username,@RequestBody String rrId){
        return rentRequestService.declineRentRequest(username,rrId);
    }


    @GetMapping("/test")
    public ResponseEntity<?> testing() {
        return rentRequestService.testing();
    }

}
