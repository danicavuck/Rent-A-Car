package com.group56.adminservice.service;

import com.group56.adminservice.client.SoapClient;
import com.group56.adminservice.model.Admin;
import com.group56.adminservice.repository.AdminRepository;
import com.group56.soap.AdminXML;
import com.group56.soap.GetAdminsRequest;
import com.group56.soap.GetAdminsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private SoapClient soapClient;

    @Autowired
    public AdminService(AdminRepository adminRepository, SoapClient soapClient) {
        this.adminRepository = adminRepository;
        this.soapClient = soapClient;
    }

    public List<AdminXML> getUsersXMLFromSOAPRequest() {
        GetAdminsRequest request = new GetAdminsRequest();
        request.setUsername("");
        GetAdminsResponse response = soapClient.getAdmins(request);
        return response.getAdminsXML();
    }

    public void saveAdminsToDatabase(List<Admin> admins) {
        admins.forEach(admin -> {
            adminRepository.save(admin);
        });
    }
}
