package com.group56.postingservice.service;

import com.group56.postingservice.DTO.AdvertDTO;
import com.group56.postingservice.model.*;
import com.group56.postingservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AdvertService {
    private UserRepository userRepository;
    private AdvertRepository advertRepository;
    private CarRepository carRepository;
    private FuelTypeRepository fuelTypeRepository;
    private TransmissionTypeRepository transmissionTypeRepository;
    private CarBrandRepository carBrandRepository;
    private CarModelRepository carModelRepository;
    private BodyTypeRepository bodyTypeRepository;

    @Autowired
    public AdvertService(UserRepository uRepo,AdvertRepository aRepo, CarRepository carRepo){
        this.userRepository = uRepo;
        this.advertRepository = aRepo;
        this.carRepository = carRepo;

    }

    public ResponseEntity<?> addAdvert(AdvertDTO advertDTO, HttpSession session){
        User user = userRepository.findUserById((Long) session.getAttribute("id"));
        if(user != null) {
            if(user.getNumberOfAdvertsPosted() < 3 && !user.isBlocked()){
                Advert advert = makeAdvertFromDTO(advertDTO);
                advert.setPublisher(user);
                advertRepository.save(advert);
                return new ResponseEntity<>("Advert successfully added!", HttpStatus.OK);
            }
            return new ResponseEntity<>("User already posted 3 adverts!",HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("User not found!" , HttpStatus.UNAUTHORIZED);
    }

    public Advert makeAdvertFromDTO(AdvertDTO advertDTO){
        Advert advert = new Advert();
        Car car = new Car();

        BodyType bodyType = bodyTypeRepository.findBodyTypeById(advertDTO.getId_bodyType());
        TransmissionType transmissionType = transmissionTypeRepository.findTransmissionTypeById(advertDTO.getId_transmissionType());
        FuelType fuelType = fuelTypeRepository.findFuelTypeById(advertDTO.getId_fuelType());
        CarBrand carBrand = carBrandRepository.findCarBrandById(advertDTO.getId_carBrand());
        CarModel carModel = carModelRepository.findCarModelById(advertDTO.getId_carModel());
        car.setBodyType(bodyType);
        car.setTransmissionType(transmissionType);
        car.setFuelType(fuelType);
        car.setCarBrand(carBrand);
        car.setCarModel(carModel);
        car.setLimitInKilometers(advertDTO.getLimitInKilometers());
        car.setMileage(advertDTO.getMileage());
        car.setNumberOfSeatsForChildren(advertDTO.getNumberOfSeatsForChildren());
        car.setRentLimited(advertDTO.isRentLimited());

        advert.setCar(car);
        advert.setCarLocation(advertDTO.getCarLocation());
        advert.setRentFrom(advertDTO.getDateStart());
        advert.setRentUntil(advertDTO.getDateEnd());

        car.setAdvert(advert);
        carRepository.save(car);


        return advert;
    }

}
