package com.group56.rentingservice.service;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.DTO.RentRequestDetailsDTO;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
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
        User user = userRepository.findUserByUsername(rentRequestDTO.getUsername());
        if(user != null) {
            ArrayList<String> owners = new ArrayList<>();
            ArrayList<Advert> adverts = new ArrayList<>();

            for (String id : rentRequestDTO.getAdvertIds()) {
                adverts.add(advertRepository.findAdvertByUuid(UUID.fromString(id)));
            }
            for (Advert a : adverts) {
                owners.add(a.getPublisher());
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
                        publisherUsername(a.getPublisher()).requestUsername(user.getUsername()).
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

//    public ResponseEntity<?> testing() {
//        User u = User.builder().username("daxydana").password("022312035").build();
//        User user = User.builder().password("skinny").username("Skinny Pete").build();
//        userRepository.save(u);
//        userRepository.save(user);
//        Advert advert = Advert.builder()
//                .car(null)
//                .carLocation("Kraljevo")
//                .rentFrom(LocalDateTime.now())
//                .rentUntil(LocalDateTime.now().plusMonths(3))
//                .price(BigDecimal.valueOf(200L))
//                .uuid(UUID.fromString("75fb7c38-d686-44fc-8c9c-156161e5697f"))
//                .publisher(u)
//                .build();
//
//        advertRepository.save(advert);
//        ArrayList<Advert> adverts = new ArrayList<Advert>();
//        adverts.add(advert);
//        RentRequest rentRequest = RentRequest.builder().requestUsername("Skinny Pete").publisherUsername(u.getUsername()).rentRequestStatus(RentRequestStatus.PENDING)
//                .active(true).advertList(adverts).bundle(true).timeStart(LocalDateTime.now()).timeEnd(LocalDateTime.now().plusMonths(1))
//                .uuid(UUID.randomUUID()).build();
//
//
//        rentRequestRepository.save(rentRequest);
//        return new ResponseEntity<>("Rent request created!",HttpStatus.CREATED);
//    }


    public ResponseEntity<?> getRentRequestsForOwner(String username){
        User user = userRepository.findUserByUsername(username);
        if(user != null){

            ArrayList<RentRequest> requests = rentRequestRepository.findRentRequestByPublisherUsername(user.getUsername());
              if(requests != null){
                ArrayList<RentRequest> fillteredRequests = new ArrayList<RentRequest>();
                for(RentRequest r : requests){
                    if(r.isActive() && r.getRentRequestStatus().equals(RentRequestStatus.PENDING))
                        fillteredRequests.add(r);
                }
                ArrayList<RentRequestDetailsDTO> rrqsts = makeDTOfromEntity(fillteredRequests);
                return new ResponseEntity<>(rrqsts,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    public ResponseEntity<?> acceptRentRequest(String username,String uuid){
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            System.out.println(uuid);
            RentRequest rr = rentRequestRepository.findRentRequestByUuid(UUID.fromString(uuid));
            if(rr == null){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }else if(!rr.getPublisherUsername().equals(username)){
                return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }

            rr.setActive(false);
            rr.setAccepted(true);
            rr.setRentRequestStatus(RentRequestStatus.PAID);
            rr.setTimeAccepted(LocalDateTime.now());
            return new ResponseEntity<>("Rent request accepted!",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);

        }

    @Transactional
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    public ResponseEntity<?> declineRentRequest(String username,String uuid){
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            RentRequest rr = rentRequestRepository.findRentRequestByUuid(UUID.fromString(uuid));
            if(rr == null){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }else if(!rr.getPublisherUsername().equals(username)){
                return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }
            rr.setActive(false);
            rr.setAccepted(false);
            rr.setRentRequestStatus(RentRequestStatus.CANCELED);
            return new ResponseEntity<>("Rent request declined!",HttpStatus.OK);

        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }



    public ArrayList<RentRequestDetailsDTO> makeDTOfromEntity(ArrayList<RentRequest> rrqsts){
        ArrayList<RentRequestDetailsDTO> requests = new ArrayList<>();
        for(RentRequest r : rrqsts){
            RentRequestDetailsDTO req = new RentRequestDetailsDTO();
            req.setUsername(r.getRequestUsername());
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
            req.setDateStart(r.getTimeStart().format(dateformatter));
            req.setDateEnd(r.getTimeEnd().format(dateformatter));
            req.setTimeStart(r.getTimeStart().format(timeformatter));
            req.setTimeEnd(r.getTimeEnd().format(timeformatter));
            req.setUuid(r.getUuid().toString());
            req.setBundle(r.isBundle());

            ArrayList<String> uuids = new ArrayList<>();
            for(RentRequest temp : rrqsts) {
                for(Advert a : temp.getAdvertList()) {
                    uuids.add(a.getUuid().toString());
                }
            }
            req.setAdvertUUIDs(uuids);
            requests.add(req);
        }

        return requests;
    }



}
