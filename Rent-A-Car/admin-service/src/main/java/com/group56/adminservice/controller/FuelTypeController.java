package com.group56.adminservice.controller;

import com.group56.adminservice.DTO.FuelTypeDTO;
import com.group56.adminservice.service.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/admin-service/fuel-type")
public class FuelTypeController {
    private FuelTypeService fuelTypeService;

    @Autowired
    public FuelTypeController(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addNewFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.addNewFuelType(fuelTypeDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> removeFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.removeFuelType(fuelTypeDTO);
    }
}
