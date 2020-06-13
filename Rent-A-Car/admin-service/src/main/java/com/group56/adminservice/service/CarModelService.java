package com.group56.adminservice.service;

import com.group56.adminservice.DTO.CarModelDTO;
import com.group56.adminservice.model.CarModel;
import com.group56.adminservice.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CarModelService {
    private CarModelRepository carModelRepository;

    @Autowired
    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Transactional
    public ResponseEntity<?> addNewCarModel(CarModelDTO carModelDTO) {
        if(carModelRepository.existsByModelName(carModelDTO.getModelName())) {
            if(!carModelDTO.getModelName().equals("")) {
                CarModel carModel = CarModel.builder().modelName(carModelDTO.getModelName()).active(true).build();
                carModelRepository.save(carModel);
                return new ResponseEntity<>("Model Created", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Invalid model name", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Model already exists", HttpStatus.FORBIDDEN);
    }

    @Transactional
    public ResponseEntity<?> updateCarModel(CarModelDTO carModelDTO) {
        if(!carModelRepository.existsByModelName(carModelDTO.getModelName())) {
            if(!carModelRepository.existsByModelName(carModelDTO.getNewModelName())) {
                CarModel carModel = carModelRepository.findByModelName(carModelDTO.getModelName());
                carModel.setModelName(carModelDTO.getNewModelName());
                carModelRepository.save(carModel);
                return new ResponseEntity<>("Model successfully updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("Model with provided name already exists", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Model doesn't exist", HttpStatus.FORBIDDEN);
    }

    @Transactional
    public ResponseEntity<?> removeCarModel(CarModelDTO carModelDTO) {
        if(carModelRepository.existsByModelName(carModelDTO.getModelName())) {
            CarModel carModel = carModelRepository.findByModelName(carModelDTO.getModelName());
            carModel.setActive(false);
            carModelRepository.save(carModel);
            return new ResponseEntity<>("Model successfully removed", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Model doesn't exist", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> getCarModels() {
        List<CarModel> carModels = carModelRepository.findAll();
        return new ResponseEntity<>(carModels, HttpStatus.OK);
    }
}
