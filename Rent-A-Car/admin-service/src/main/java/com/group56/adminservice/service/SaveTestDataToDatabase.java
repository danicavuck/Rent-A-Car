package com.group56.adminservice.service;

import com.group56.adminservice.model.*;
import com.group56.adminservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveTestDataToDatabase {
    private FuelTypeRepository fuelTypeRepository;
    private CarBrandRepository carBrandRepository;
    private CarModelRepository carModelRepository;
    private BodyTypeRepository bodyTypeRepository;
    private TransmissionTypeRepository transmissionTypeRepository;

    @Autowired
    public SaveTestDataToDatabase(FuelTypeRepository fuel, CarBrandRepository brand,
                                  CarModelRepository model, BodyTypeRepository body,
                                  TransmissionTypeRepository transmission) {
        fuelTypeRepository = fuel;
        carBrandRepository = brand;
        carModelRepository = model;
        transmissionTypeRepository = transmission;
        bodyTypeRepository = body;
    }

    public void saveData() {
        saveFuelType();
        saveBrand();
        saveModel();
        saveTransmission();
        saveBodyType();
    }

    private void saveFuelType() {
        FuelType diesel = FuelType.builder()
                .fuelType("Diesel")
                .isActive(true)
                .build();
        FuelType petrol = FuelType.builder()
                .fuelType("Petrol")
                .isActive(true)
                .build();
        FuelType gas = FuelType.builder()
                .fuelType("Gas")
                .isActive(true)
                .build();
        FuelType electric = FuelType.builder()
                .fuelType("Electric")
                .isActive(true)
                .build();
        fuelTypeRepository.save(diesel);
        fuelTypeRepository.save(petrol);
        fuelTypeRepository.save(gas);
        fuelTypeRepository.save(electric);
    }

    private void saveBrand() {
        CarBrand mercedes = CarBrand.builder()
                .brandName("Mercedes")
                .isActive(true)
                .build();
        CarBrand audi = CarBrand.builder()
                .brandName("Audi")
                .isActive(true)
                .build();
        CarBrand bmw = CarBrand.builder()
                .brandName("BMW")
                .isActive(true)
                .build();
        CarBrand ford = CarBrand.builder()
                .brandName("Ford")
                .isActive(true)
                .build();

        carBrandRepository.save(mercedes);
        carBrandRepository.save(audi);
        carBrandRepository.save(bmw);
        carBrandRepository.save(ford);
    }

    private void saveModel(){
        CarModel a6 = CarModel.builder()
                .modelName("A6")
                .active(true)
                .build();
        CarModel e220d = CarModel.builder()
                .modelName("E 220d")
                .active(true)
                .build();
        CarModel m3 = CarModel.builder()
                .modelName("M3")
                .active(true)
                .build();
        CarModel focus = CarModel.builder()
                .modelName("Focus")
                .active(true)
                .build();

        carModelRepository.save(a6);
        carModelRepository.save(e220d);
        carModelRepository.save(m3);
        carModelRepository.save(focus);
    }

    private void saveTransmission() {
        TransmissionType manual = TransmissionType.builder()
                .transmissionType("Manual")
                .isActive(true)
                .build();

        TransmissionType automatic = TransmissionType.builder()
                .transmissionType("Automatic")
                .isActive(true)
                .build();

        transmissionTypeRepository.save(manual);
        transmissionTypeRepository.save(automatic);
    }

    private void saveBodyType() {
        BodyType sedan = BodyType.builder()
                .bodyType("Sedan")
                .isActive(true)
                .build();

        BodyType suv = BodyType.builder()
                .bodyType("SUV")
                .isActive(true)
                .build();

        BodyType coupe = BodyType.builder()
                .bodyType("Coupe")
                .isActive(true)
                .build();

        BodyType estate = BodyType.builder()
                .bodyType("Estate")
                .isActive(true)
                .build();

        BodyType hatchback = BodyType.builder()
                .bodyType("Hatchback")
                .isActive(true)
                .build();

        bodyTypeRepository.save(sedan);
        bodyTypeRepository.save(suv);
        bodyTypeRepository.save(coupe);
        bodyTypeRepository.save(estate);
        bodyTypeRepository.save(hatchback);
    }
}
