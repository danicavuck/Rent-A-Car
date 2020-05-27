package com.group56.adminservice.service;

import com.group56.adminservice.DTO.TransmissionTypeDTO;
import com.group56.adminservice.model.TransmissionType;
import com.group56.adminservice.repository.TransmissionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransmissionTypeService {
    private TransmissionTypeRepository transmissionTypeRepository;

    @Autowired
    public TransmissionTypeService(TransmissionTypeRepository transmissionTypeRepository) {
        this.transmissionTypeRepository = transmissionTypeRepository;
    }

    @Transactional
    public ResponseEntity<?> addNewTransmissionType(TransmissionTypeDTO transmissionTypeDTO) {
        if(!transmissionTypeRepository.existsByTransmissionType(transmissionTypeDTO.getTransmissionType())) {
            TransmissionType transmissionType = TransmissionType.builder().transmissionType(transmissionTypeDTO.getTransmissionType()).isActive(true).build();
            transmissionTypeRepository.save(transmissionType);
            return new ResponseEntity<>("Transmission type created!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Transmission type already exists!", HttpStatus.FORBIDDEN);
    }

    @Transactional
    public ResponseEntity<?> removeTransmissionType(TransmissionTypeDTO transmissionTypeDTO) {
        if(transmissionTypeRepository.existsByTransmissionType(transmissionTypeDTO.getTransmissionType())){
            TransmissionType transmissionType = transmissionTypeRepository.findByTransmissionType(transmissionTypeDTO.getTransmissionType());
            transmissionType.setActive(false);
            transmissionTypeRepository.save(transmissionType);
            return new ResponseEntity<>("Transmission Type successfully removed!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Transmission Type not found", HttpStatus.NOT_FOUND);
    }
}
