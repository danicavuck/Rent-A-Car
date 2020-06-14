package com.group56.adminservice.controller;

import com.group56.adminservice.DTO.BrandDTO;
import com.group56.adminservice.service.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/admin-service/brand")
public class CarBrandController {
    private CarBrandService carBrandService;

    @Autowired
    public CarBrandController(CarBrandService carBrandService) {
        this.carBrandService = carBrandService;
    }

    @GetMapping
    public ResponseEntity<?> getCarBrands() {
        return carBrandService.getCarBrands();
    }

    @PostMapping
    public ResponseEntity<?> addNewBrand(@RequestBody BrandDTO brandDTO) {
        return carBrandService.addNewBrand(brandDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateBrand(@RequestBody BrandDTO brandDTO) {
        return carBrandService.modifyBrandName(brandDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> removeBrand(@RequestBody BrandDTO brandDTO) {
        return carBrandService.removeBrand(brandDTO);
    }
}
