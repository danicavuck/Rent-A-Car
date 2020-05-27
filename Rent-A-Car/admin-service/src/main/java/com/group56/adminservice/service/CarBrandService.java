package com.group56.adminservice.service;

import com.group56.adminservice.DTO.BrandDTO;
import com.group56.adminservice.model.CarBrand;
import com.group56.adminservice.repository.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CarBrandService {
    private CarBrandRepository carBrandRepository;

    @Autowired
    public CarBrandService(CarBrandRepository carBrandRepository) {
        this.carBrandRepository = carBrandRepository;
    }

    @Transactional
    public ResponseEntity<?> addNewBrand(BrandDTO brandDTO) {
        if(!carBrandRepository.existsByBrandName(brandDTO.getBrandName()) && !brandDTO.getBrandName().equals("")){
            CarBrand carBrand = CarBrand.builder().brandName(brandDTO.getBrandName()).isActive(true).build();
            carBrandRepository.save(carBrand);
            return new ResponseEntity<>("Car brand added", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Invalid brand name", HttpStatus.FORBIDDEN);
    }

    @Transactional
    public ResponseEntity<?> removeBrand(BrandDTO brandDTO) {
        if(carBrandRepository.existsByBrandName(brandDTO.getBrandName())) {
            CarBrand carBrand = carBrandRepository.findByBrandName(brandDTO.getBrandName());
            if(carBrand.isActive()) {
                carBrand.setActive(false);
                carBrandRepository.save(carBrand);
                return new ResponseEntity<>("Car brand successfully removed", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("Brand has already been deleted", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Brand name not found", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> modifyBrandName(BrandDTO brandDTO) {
        if(carBrandRepository.existsByBrandName(brandDTO.getBrandName())){
            if(!carBrandRepository.existsByBrandName(brandDTO.getNewBrandName()) && !brandDTO.getNewBrandName().equals("")) {
                CarBrand carBrand = carBrandRepository.findByBrandName(brandDTO.getBrandName());
                carBrand.setBrandName(brandDTO.getNewBrandName());
                carBrandRepository.save(carBrand);
                return new ResponseEntity<>("Car brand added", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("New Brand name is not valid", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Old brand name is not found", HttpStatus.FORBIDDEN);
    }
}
