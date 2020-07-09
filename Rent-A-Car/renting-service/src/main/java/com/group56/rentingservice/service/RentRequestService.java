package com.group56.rentingservice.service;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.model.User;
import com.group56.rentingservice.repository.AdvertRepository;
import com.group56.rentingservice.repository.RentRequestRepository;
import com.group56.rentingservice.repository.UserRepository;
import com.group56.rentingservice.util.RentRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.LockModeType;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RentRequestService {

    private RentRequestRepository rentRequestRepository;
    private UserRepository userRepository;
    private AdvertRepository advertRepository;

    @Autowired
    public RentRequestService(RentRequestRepository rentRequestRepository,UserRepository userRepo,AdvertRepository aRepo){
        this.rentRequestRepository = rentRequestRepository;
        this.userRepository = userRepo;
        this.advertRepository = aRepo;
    }

    //bundle - single
    public ResponseEntity<?> addRentRequest(@RequestBody RentRequestDTO rentRequestDTO){
        User u = User.builder().username("daxydana").id((long) 1).build();
        userRepository.save(u);
        User user = userRepository.findUserByUsername(rentRequestDTO.getUsername());
        if(user != null) {
            ArrayList<String> owners = new ArrayList<>();
            ArrayList<Advert> adverts = new ArrayList<>();

            for (String id : rentRequestDTO.getAdvertIds()) {
                System.out.println("doslo1");
                adverts.add(advertRepository.findAdvertByUuid(UUID.fromString(id)));
                System.out.println("doslo2");
            }
            for (Advert a : adverts) {
                owners.add(a.getPublisher().getUsername());
            }
            if (verifyAllEqualUsingALoop(owners)) {
                if (rentRequestDTO.isBundle()) {
                    RentRequest rr = RentRequest.builder().advertList(adverts).bundle(true).publisherUsername(owners.get(0))
                            .requestUsername(user.getUsername()).rentRequestStatus(RentRequestStatus.PENDING).active(true).build();
                    rentRequestRepository.save(rr);
                    return new ResponseEntity<>("Rent request added (bundle)!", HttpStatus.CREATED);
                }
            }
            for (Advert a : adverts) {
                ArrayList<Advert> adverts1 = new ArrayList<>();
                adverts1.add(a);
                RentRequest rr = RentRequest.builder().advertList(adverts1).active(true).bundle(false).
                        publisherUsername(a.getPublisher().getUsername()).requestUsername(user.getUsername()).
                        rentRequestStatus(RentRequestStatus.PENDING).build();
                rentRequestRepository.save(rr);
            }
            return new ResponseEntity<>("Rent requests added!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User not found!",HttpStatus.UNAUTHORIZED);
    }


    public boolean verifyAllEqualUsingALoop(List<String> list) {
        for (String s : list) {
            if (!s.equals(list.get(0)))
                return false;
        }
        return true;
    }
    public ResponseEntity<?> getRentRequestsForAdvert(Advert advert, HttpSession session) {
        User user = userRepository.findUserById((Long)session.getAttribute("id"));
        if(user!=null) {
            List<RentRequest> rrqsts = advert.getRentRequests();
            if(rrqsts == null){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(rrqsts,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> getRentRequestsForOwner(String username){
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            ArrayList<RentRequest> requests = rentRequestRepository.findRentRequestByPublisherUsername(user.getUsername());
            if(requests != null){
                return new ResponseEntity<>(requests,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    public ResponseEntity<?> acceptRentRequest(Long rrId, String username){
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            RentRequest rr = rentRequestRepository.getOne(rrId);

            if(rr == null ){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            rr.setActive(false);
            rr.setAccepted(true);
            rr.setRentRequestStatus(RentRequestStatus.PAID);
            rr.setTimeAccepted(LocalDateTime.now());
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);

        }

    @Transactional
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    public ResponseEntity<?> declineRentRequest(Long rrId, String username){
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            RentRequest rr = rentRequestRepository.getOne(rrId);
            if(rr == null){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            rr.setActive(false);
            rr.setAccepted(false);
            rr.setRentRequestStatus(RentRequestStatus.CANCELED);

        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }



}
