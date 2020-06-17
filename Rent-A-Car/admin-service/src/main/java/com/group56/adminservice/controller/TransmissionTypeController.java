package com.group56.adminservice.controller;

import com.group56.adminservice.DTO.TransmissionTypeDTO;
import com.group56.adminservice.service.TransmissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin-service/transmission-type")
public class TransmissionTypeController {
    private TransmissionTypeService transmissionTypeService;

    @Autowired
    public TransmissionTypeController(TransmissionTypeService transmissionTypeService) {
        this.transmissionTypeService = transmissionTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTransmissionTypes(){
        return transmissionTypeService.getAllTransmissionTypes();
    }

    @PostMapping
    public ResponseEntity<?> addNewTransmissionType(@RequestBody TransmissionTypeDTO transmissionTypeDTO) {
        return transmissionTypeService.addNewTransmissionType(transmissionTypeDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> removeTransmissionType(@RequestBody TransmissionTypeDTO transmissionTypeDTO) {
        return transmissionTypeService.removeTransmissionType(transmissionTypeDTO);
    }
}
