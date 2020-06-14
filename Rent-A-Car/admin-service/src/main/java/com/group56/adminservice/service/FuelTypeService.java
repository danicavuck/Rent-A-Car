package com.group56.adminservice.service;

import com.group56.adminservice.DTO.FuelTypeDTO;
import com.group56.adminservice.model.FuelType;
import com.group56.adminservice.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FuelTypeService {
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    public FuelTypeService(FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
    }

    @Transactional
    public ResponseEntity<?> addNewFuelType(FuelTypeDTO fuelTypeDTO) {
        if(!fuelTypeRepository.existsByFuelType(fuelTypeDTO.getFuelType())){
            FuelType fuelType = FuelType.builder().fuelType(fuelTypeDTO.getFuelType()).isActive(true).build();
            fuelTypeRepository.save(fuelType);
            return new ResponseEntity<>("Fuel Type created!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Fuel Type already exists!", HttpStatus.FORBIDDEN);
    }

    @Transactional
    public ResponseEntity<?> removeFuelType(FuelTypeDTO fuelTypeDTO) {
        if(fuelTypeRepository.existsByFuelType(fuelTypeDTO.getFuelType())) {
            FuelType fuelType = fuelTypeRepository.findByFuelType(fuelTypeDTO.getFuelType());
            fuelType.setActive(false);
            fuelTypeRepository.save(fuelType);
            return new ResponseEntity<>("Fuel Type successfully removed", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Fuel Type doesn't exist", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllFuelTypes() {
        List<FuelType> fuelTypes = fuelTypeRepository.findAll();
        return new ResponseEntity<>(fuelTypes, HttpStatus.OK);
    }
}
