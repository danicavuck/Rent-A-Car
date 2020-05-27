package com.group56.adminservice.service;

import com.group56.adminservice.DTO.BodyTypeDTO;
import com.group56.adminservice.model.BodyType;
import com.group56.adminservice.repository.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BodyTypeService {
    private BodyTypeRepository bodyTypeRepository;

    @Autowired
    public BodyTypeService(BodyTypeRepository bodyTypeRepository) {
        this.bodyTypeRepository = bodyTypeRepository;
    }

    @Transactional
    public ResponseEntity<?> addNewBodyType(BodyTypeDTO bodyTypeDTO) {
        if(!bodyTypeRepository.existsByBodyType(bodyTypeDTO.getBodyType())) {
            BodyType bodyType = BodyType.builder().bodyType(bodyTypeDTO.getBodyType()).isActive(true).build();
            bodyTypeRepository.save(bodyType);
            return new ResponseEntity<>("Body Type created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Body Type already exists", HttpStatus.FORBIDDEN);
    }

    @Transactional
    public ResponseEntity<?> removeBodyType(BodyTypeDTO bodyTypeDTO) {
        if(bodyTypeRepository.existsByBodyType(bodyTypeDTO.getBodyType())) {
            BodyType bodyType = bodyTypeRepository.findByBodyType(bodyTypeDTO.getBodyType());
            bodyType.setActive(false);
            bodyTypeRepository.save(bodyType);
            return new ResponseEntity<>("Body Type successfully deleted", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Body Type doesn't exist!", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> updateBodyType(BodyTypeDTO bodyTypeDTO) {
        if(bodyTypeRepository.existsByBodyType(bodyTypeDTO.getBodyType())) {
            if(!bodyTypeRepository.existsByBodyType(bodyTypeDTO.getNewBodyType()) && !bodyTypeDTO.getNewBodyType().equals("")) {
                BodyType bodyType = bodyTypeRepository.findByBodyType(bodyTypeDTO.getBodyType());
                bodyType.setBodyType(bodyTypeDTO.getNewBodyType());
                bodyTypeRepository.save(bodyType);
                return new ResponseEntity<>("Body Type successfully updated!", HttpStatus.OK);
            }
            return new ResponseEntity<>("New Body Type name is not valid", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Body Type doesn't exist", HttpStatus.NOT_FOUND);
    }
}
