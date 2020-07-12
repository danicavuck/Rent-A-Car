package com.group56.rentingservice.service;

import com.group56.rentingservice.DTO.RentRequestDTO;
import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.model.Car;
import com.group56.rentingservice.model.RentRequest;
import com.group56.rentingservice.model.User;
import com.group56.rentingservice.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertService {

    private AdvertRepository advertRepository;
    private CarRepository carRepository;
    private BodyTypeRepository bodyTypeRepository;
    private CarBrandRepository carBrandRepository;
    private CarModelRepository carModelRepository;
    private FuelTypeRepository fuelTypeRepository;
    private TransmissionTypeRepository transmissionTypeRepository;
    private Logger logger = LoggerFactory.getLogger(AdvertService.class);

    @Autowired
    public AdvertService(AdvertRepository advertRepository, CarRepository carRepository, BodyTypeRepository btr,
                         CarBrandRepository cbr, CarModelRepository cmr, FuelTypeRepository ftr, TransmissionTypeRepository tr) {
        this.advertRepository = advertRepository;
        this.carRepository = carRepository;
        this.bodyTypeRepository = btr;
        this.carBrandRepository = cbr;
        this.carModelRepository = cmr;
        this.fuelTypeRepository = ftr;
        this.transmissionTypeRepository = tr;
    }

    public void fetchNewAdverts() {
        RestTemplate restTemplate = new RestTemplate();
        String apiEndpoint = "http://localhost:8080/posting-service/advert/rent-service";
        ResponseEntity<Advert[]> response = restTemplate.getForEntity(apiEndpoint, Advert[].class);
        Advert[] adverts = response.getBody();

        for(int i = 0; i< adverts.length; i++) {
            saveAdvert(adverts[i]);
        }
    }

    private void saveAdvert(Advert advert) {
        Car car = advert.getCar();
        bodyTypeRepository.save(car.getBodyType());
        carBrandRepository.save(car.getCarBrand());
        carModelRepository.save(car.getCarModel());
        fuelTypeRepository.save(car.getFuelType());
        transmissionTypeRepository.save(car.getTransmissionType());
        carRepository.save(car);

        advert.setActive(true);
        logger.info("------------------------------------------------------------\n\n\n");
        logger.info(advert.toString());
        advertRepository.save(advert);
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
