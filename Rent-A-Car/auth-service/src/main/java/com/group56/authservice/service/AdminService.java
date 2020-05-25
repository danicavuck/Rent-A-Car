package com.group56.authservice.service;

import com.group56.authservice.DTO.AdminDTO;
import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.model.Admin;
import com.group56.authservice.repository.AdminRepository;
import com.group56.authservice.utility.IdentityCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private IdentityCheck identityCheck;

    @Autowired
    public AdminService(AdminRepository adminRepository, IdentityCheck identityCheck) {
        this.adminRepository = adminRepository;
        this.identityCheck = identityCheck;
    }

    @Transactional
    public ResponseEntity<?> registerNewAdmin(AdminDTO adminDTO) {
        if(identityCheck.isUsernameUnique(adminDTO.getAdminName())){
            if(identityCheck.isRegistrationNumberUnique(adminDTO.getRegistrationNumber())) {
                Admin admin = Admin.builder().adminName(adminDTO.getAdminName()).firstName(adminDTO.getFirstName()).lastName(adminDTO.getLastName()).address(adminDTO.getAddress()).password(adminDTO.getPassword()).registrationNumber(adminDTO.getRegistrationNumber()).build();
                adminRepository.save(admin);

                return new ResponseEntity<>("Admin created!", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Registration number is not uniqe!", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Admin username is not unique!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> logInAdmin(LoginDTO loginDTO){
        Admin admin = adminRepository.findAdminByAdminName(loginDTO.getUsername());
        if(admin.getPassword().equals(loginDTO.getPassword()))
            return new ResponseEntity<>("OK", HttpStatus.OK);
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }
}
