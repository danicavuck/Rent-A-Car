package com.group56.adminservice.controller;

import com.group56.adminservice.DTO.CarModelDTO;
import com.group56.adminservice.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/admin-service/model")
public class CarModelController {
    private CarModelService carModelService;

    @Autowired
    public CarModelController(CarModelService carModelService){
        this.carModelService = carModelService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addNewModel(@RequestBody CarModelDTO carModelDTO){
        return carModelService.addNewCarModel(carModelDTO);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateModel(@RequestBody CarModelDTO carModelDTO) {
        return carModelService.updateCarModel(carModelDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> removeModel(@RequestBody CarModelDTO carModelDTO) {
        return carModelService.removeCarModel(carModelDTO);
    }
}
