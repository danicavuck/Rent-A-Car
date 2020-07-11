package com.group56.rentingservice.service;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.model.User;
import com.group56.rentingservice.repository.AdvertRepository;
import com.group56.rentingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertService {

    private UserRepository userRepository;
    private AdvertRepository advertRepository;

    @Autowired
    public AdvertService(UserRepository uRepo, AdvertRepository aRepo){
        userRepository = uRepo;
        advertRepository = aRepo;
    }

//    public ResponseEntity<?> getAdvertsForUser(HttpSession session) {
//        User user = userRepository.findUserById((Long)session.getAttribute("id"));
//        if(user!=null) {
//            ArrayList<Advert> adverts = advertRepository.findAdvertsByPublisher(user);
//            if(adverts == null){
//                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(adverts,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//    }
//
//    public ResponseEntity<?> getRentRequestsForAdvert(Advert advert, HttpSession session) {
//        User user = userRepository.findUserById((Long)session.getAttribute("id"));
//        if(user!=null) {
//            List<RentRequest> rrqsts = advert.getRentRequests();
//            if(rrqsts == null){
//                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(rrqsts,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//    }
}
