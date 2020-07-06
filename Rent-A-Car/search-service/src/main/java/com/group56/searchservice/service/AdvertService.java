package com.group56.searchservice.service;

import com.group56.searchservice.model.Advert;
import com.group56.searchservice.model.Car;
import com.group56.searchservice.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        String apiEndpoint = "http://localhost:8080/posting-service/advert/search-service";
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

}
