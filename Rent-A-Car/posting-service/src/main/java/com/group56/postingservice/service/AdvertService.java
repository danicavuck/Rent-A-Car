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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        User user = User.builder().username("stud_user").isBlocked(false).isActive(true).email("user123").build();
        if(user != null) {
            if(user.getNumberOfAdvertsPosted() < 3 && !user.isBlocked()){
                Advert advert = makeAdvertFromDTO(advertDTO);
                user.setNumberOfAdvertsPosted(user.getNumberOfAdvertsPosted() + 1);
                advert.setPublisher(user);
                advertRepository.save(advert);

                String msg = "ADVERT_ADDED";
                messagePublisher.sendAMessageToQueue(msg);
                return new ResponseEntity<>(advert.getUuid().toString(), HttpStatus.OK);
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
        advert.setPrice(advertDTO.getPrice());
        advert.setUuid(UUID.randomUUID());

        car.setAdvert(advert);
        carRepository.save(car);

        return advert;
    }

    public ResponseEntity<?> testing() {
        BodyType bodyType = BodyType.builder().isActive(true).bodyType("Sedan").build();
        TransmissionType transmission = TransmissionType.builder().isActive(true).transmissionType("Automatic").build();
        FuelType fuelType = FuelType.builder().fuelType("Diesel").isActive(true).build();
        CarBrand carBrand = CarBrand.builder().isActive(true).brandName("Audi").build();
        CarModel carModel = CarModel.builder().modelName("A6").active(true).build();
        User user = User.builder().username("Skinny Pete").build();
        Car car = Car.builder()
                .bodyType(bodyType).transmissionType(transmission)
                .fuelType(fuelType).carBrand(carBrand)
                .carModel(carModel).limitInKilometers(100000L)
                .mileage(10000L).numberOfSeatsForChildren(4).isRentLimited(true)
                .build();

        Advert advert = Advert.builder()
                .car(car)
                .carLocation("Kraljevo")
                .rentFrom(LocalDateTime.now())
                .rentUntil(LocalDateTime.now().plusMonths(3))
                .price(BigDecimal.valueOf(200L))
                .uuid(UUID.fromString("75fb7c38-d686-44fc-8c9c-156161e5697f"))
                .publisher(user)
                .build();

        car.setAdvert(advert);
        advertRepository.save(advert);
        return new ResponseEntity<>("Advert saved", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAdverts() {
        List<Advert> adverts = advertRepository.findAll();
        List<AdvertDTO> advertDTOs = mapAdvertsToDto(adverts);

        return new ResponseEntity<>(advertDTOs, HttpStatus.OK);
    }

    private List<AdvertDTO> mapAdvertsToDto(List<Advert> adverts) {
        List<AdvertDTO> dtos = new ArrayList<>();

        adverts.forEach(advert -> {
            AdvertDTO dto = AdvertDTO.builder()
                    .dateStart(advert.getRentFrom())
                    .dateEnd(advert.getRentUntil())
                    .price(advert.getPrice())
                    .uuid(advert.getUuid().toString())
                    .bodyType(advert.getCar().getBodyType().getBodyType())
                    .fuel(advert.getCar().getFuelType().getFuelType())
                    .model(advert.getCar().getCarModel().getModelName())
                    .brand(advert.getCar().getCarBrand().getBrandName())
                    .transmission(advert.getCar().getTransmissionType().getTransmissionType())
                    .mileage(advert.getCar().getMileage())
                    .isRentLimited(advert.getCar().isRentLimited())
                    .limitInKilometers(advert.getCar().getLimitInKilometers())
                    .numberOfSeatsForChildren(advert.getCar().getNumberOfSeatsForChildren())
                    .carLocation(advert.getCarLocation())
                    .username(advert.getPublisher().getUsername())
                    .build();

            dtos.add(dto);
        });

        return dtos;
    }

    public ResponseEntity<?> getSingleAdvert(String uuid) {
        Advert advert = advertRepository.findAdvertByUuid(UUID.fromString(uuid));
        List<Advert> adverts = new ArrayList<>();
        adverts.add(advert);
        if(advert != null) {
            return new ResponseEntity<>(mapAdvertsToDto(adverts), HttpStatus.OK);
        }
        return new ResponseEntity<>("Advert with provided UUID found", HttpStatus.NOT_FOUND);
    }
}
