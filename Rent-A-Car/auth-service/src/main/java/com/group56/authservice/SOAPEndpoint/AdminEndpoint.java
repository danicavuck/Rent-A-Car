package com.group56.authservice.SOAPEndpoint;

import com.group56.authservice.model.Admin;
import com.group56.authservice.service.AdminService;
import com.group56.soap_package.AdminXML;
import com.group56.soap_package.GetAdminsRequest;
import com.group56.soap_package.GetAdminsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class AdminEndpoint {
    private AdminService adminService;

    @Autowired
    public AdminEndpoint(AdminService adminService) {
        this.adminService = adminService;
    }

    @PayloadRoot(namespace = "http://group56.com/soap-package", localPart = "getAdminsRequest")
    public GetAdminsResponse getAdminsRequest(@RequestPayload GetAdminsRequest request) {
        GetAdminsResponse response = new GetAdminsResponse();
        List<Admin> admins = adminService.getAdminsThatAreNotSharedWithAdminService(request.getUsername());
        List<AdminXML> adminsXML = convertAdminsToXML(admins);
        response.setAdminsXML(adminsXML);

        return response;
    }

    private List<AdminXML> convertAdminsToXML(List<Admin> admins) {
        List<AdminXML> adminsXML = new ArrayList<>();

        admins.forEach(data -> {
            AdminXML admin = new AdminXML();
            admin.setAdminName(data.getAdminName());
            admin.setLastName(data.getLastName());
            admin.setAddress(data.getAddress());
            admin.setEmail(data.getEmail());
            admin.setFirstName(data.getFirstName());

            adminsXML.add(admin);
        });

        return adminsXML;
    }
}
