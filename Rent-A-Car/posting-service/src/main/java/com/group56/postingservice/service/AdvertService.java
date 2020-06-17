package com.group56.postingservice.service;

import com.group56.postingservice.DTO.AdvertDTO;
import com.group56.postingservice.DTO.AdvertUpdateDTO;
import com.group56.postingservice.model.*;
import com.group56.postingservice.repository.*;
import com.group56.postingservice.util.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    private RentRequestRepository rentRequestRepository;
    private MessagePublisher messagePublisher;

    @Autowired
    public AdvertService(UserRepository uRepo,AdvertRepository aRepo, CarRepository carRepo,
                         FuelTypeRepository fRepo, TransmissionTypeRepository tRepo,CarBrandRepository cbRepo,
                         CarModelRepository cmRepo, BodyTypeRepository btRepo,RentRequestRepository rrRepo,
                         MessagePublisher broker){
        this.userRepository = uRepo;
        this.advertRepository = aRepo;
        this.carRepository = carRepo;
        this.fuelTypeRepository = fRepo;
        this.transmissionTypeRepository = tRepo;
        this.carBrandRepository = cbRepo;
        this.carModelRepository = cmRepo;
        this.bodyTypeRepository = btRepo;
        this.rentRequestRepository = rrRepo;
        this.messagePublisher = broker;
    }

    public ResponseEntity<?> addAdvert(AdvertDTO advertDTO){
        //User user = userRepository.findUserByUsername(advertDTO.getUsername());
        User user = User.builder().username("stub_user").build();
        if(user != null) {
            if(user.getNumberOfAdvertsPosted() < 3 && !user.isBlocked()){
                Advert advert = makeAdvertFromDTO(advertDTO);
                advert.setPublisher(user);
                advertRepository.save(advert);

                String msg = "ADVERT_ADDED";
                messagePublisher.sendAMessageToQueue(msg);
                return new ResponseEntity<>("Advert successfully added!", HttpStatus.OK);
            }
            return new ResponseEntity<>("User already posted 3 adverts!",HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("User not found!" , HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> updateAdvert(AdvertUpdateDTO advertUpdateDTO, HttpSession session){
        User user = userRepository.findUserById((Long) session.getAttribute("id"));
        if(user != null) {
                Advert advert = advertRepository.findAdvertById(advertUpdateDTO.getId());
                if(advert.getPublisher() == user) {
                    advert.setRentFrom(advertUpdateDTO.getRentFrom());
                    advert.setRentUntil(advertUpdateDTO.getRentUntil());
                    List<RentRequest> requests = advert.getRentRequests();
                    List<RentRequest> emptyList = new ArrayList<RentRequest>();
                    if(requests != null){
                        for(RentRequest r : requests){
                            RentRequest req = rentRequestRepository.findRentRequestById(r.getId());
                            req.setActive(false);
                        }
                        advert.setRentRequests(emptyList);
                    }
                    return new ResponseEntity<>("Advert successfully updated!", HttpStatus.OK);
                }
                return new ResponseEntity<>("User doesn't have permission to update this entity!" , HttpStatus.UNAUTHORIZED);
            }

        return new ResponseEntity<>("User not found!" , HttpStatus.UNAUTHORIZED);
    }


    public Advert makeAdvertFromDTO(AdvertDTO advertDTO){
        Advert advert = Advert.builder().build();
        BodyType bodyType = BodyType.builder()
                .bodyType(advertDTO.getBodyType())
                .build();
        TransmissionType transmission = TransmissionType.builder()
                .transmissionType(advertDTO.getTransmission())
                .build();
        FuelType fuelType = FuelType.builder()
                .fuelType(advertDTO.getFuel())
                .build();
        CarBrand carBrand = CarBrand.builder()
                .brandName(advertDTO.getBrand())
                .build();
        CarModel model = CarModel.builder()
                .modelName(advertDTO.getModel())
                .build();
        Car car = Car.builder()
                .bodyType(bodyType)
                .transmissionType(transmission)
                .fuelType(fuelType)
                .carBrand(carBrand)
                .carModel(model)
                .limitInKilometers(advertDTO.getLimitInKilometers())
                .mileage(advertDTO.getMileage())
                .numberOfSeatsForChildren(advertDTO.getNumberOfSeatsForChildren())
                .isRentLimited(advertDTO.isRentLimited())
                .build();


        advert.setCar(car);
        advert.setCarLocation(advertDTO.getCarLocation());
        advert.setRentFrom(advertDTO.getDateStart());
        advert.setRentUntil(advertDTO.getDateEnd());

        car.setAdvert(advert);
        carRepository.save(car);

        return advert;
    }

}
