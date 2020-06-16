package com.group56.authservice.service;

import com.group56.authservice.DTO.AdminDTO;
import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.enumeration.Role;
import com.group56.authservice.model.Admin;
import com.group56.authservice.model.Authorization;
import com.group56.authservice.repository.AdminRepository;
import com.group56.authservice.utility.IdentityCheck;
import com.group56.authservice.utility.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private IdentityCheck identityCheck;
    private MessagePublisher messagePublisher;

    @Autowired
    public AdminService(AdminRepository adminRepository, IdentityCheck identityCheck, MessagePublisher messagePublisher) {
        this.adminRepository = adminRepository;
        this.identityCheck = identityCheck;
        this.messagePublisher = messagePublisher;
    }

    @Transactional
    public ResponseEntity<?> registerNewAdmin(AdminDTO adminDTO) {
        if(identityCheck.isUsernameUnique(adminDTO.getAdminName())){
            if(identityCheck.isEmailAddressUnique(adminDTO.getEmail())) {
                Admin admin = createAdminFromDTO(adminDTO);
                adminRepository.save(admin);

                messagePublisher.sendAMessageToQueue("ADMIN_ADDED");
                return new ResponseEntity<>("Account successfully created", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Email address is already in use", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Admin username is not unique!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> logInAdmin(LoginDTO loginDTO, HttpSession session){
        Admin admin = adminRepository.findAdminByAdminName(loginDTO.getUsername());
        if(admin.getPassword().equals(loginDTO.getPassword())){
            session.setAttribute("ROLE", Role.ADMIN);
            session.setAttribute("USERNAME", admin.getAdminName());
            return new ResponseEntity<>("ADMIN", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    private Admin createAdminFromDTO(AdminDTO adminDTO) {
        return Admin.builder().adminName(adminDTO.getAdminName())
                .firstName(adminDTO.getFirstName())
                .lastName(adminDTO.getLastName())
                .address(adminDTO.getAddress())
                .password(adminDTO.getPassword())
                .isSharedWithAdmin(false)
                .email(adminDTO.getEmail()).build();
    }

    public List<Admin> getAdminsThatAreNotSharedWithAdminService(String username) {
        List<Admin> admins = adminRepository.findAll();
        List<Admin> relevantAdmins = new ArrayList<>();

        admins.forEach(admin -> {
            if(!admin.isSharedWithAdmin()){
                relevantAdmins.add(admin);
                admin.setSharedWithAdmin(true);
                adminRepository.save(admin);
            }
        });

        return relevantAdmins;
    }
}
