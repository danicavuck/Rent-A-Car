package com.group56.adminservice.controller;

import com.group56.adminservice.DTO.FuelTypeDTO;
import com.group56.adminservice.service.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin-service/fuel-type")
public class FuelTypeController {
    private FuelTypeService fuelTypeService;

    @Autowired
    public FuelTypeController(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getActiveFuelTypes() {
        return fuelTypeService.getActiveFuelTypes();
    }

    @PostMapping
    public ResponseEntity<?> addNewFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.addNewFuelType(fuelTypeDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> removeFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.removeFuelType(fuelTypeDTO);
    }
}
