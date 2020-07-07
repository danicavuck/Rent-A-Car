package com.group56.adminservice.controller;

import com.group56.adminservice.DTO.BodyTypeDTO;
import com.group56.adminservice.service.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin-service/body-type")
public class BodyTypeController {
    private BodyTypeService bodyTypeService;

    @Autowired
    public BodyTypeController(BodyTypeService bodyTypeService) {
        this.bodyTypeService = bodyTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getActiveBodyTypes() {
        return bodyTypeService.getActiveBodyTypes();
    }

    @PostMapping
    public ResponseEntity<?> addNewBodyType(@RequestBody BodyTypeDTO bodyTypeDTO) {
        return bodyTypeService.addNewBodyType(bodyTypeDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateBodyType(@RequestBody BodyTypeDTO bodyTypeDTO) {
        return bodyTypeService.updateBodyType(bodyTypeDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> removeBodyType(@RequestBody BodyTypeDTO bodyTypeDTO) {
        return bodyTypeService.removeBodyType(bodyTypeDTO);
    }
}
