package com.group56.authservice.SOAPEndpoint;

import com.group56.authservice.model.User;
import com.group56.authservice.service.UserService;
import com.group56.soap_package.GetUsersRequest;
import com.group56.soap_package.GetUsersResponse;
import com.group56.soap_package.UserXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class UserEndpoint {
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = "http://group56.com/soap-package", localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getUsersRequest(@RequestPayload GetUsersRequest getUsersRequest){
        logger.info("Creating response");
        GetUsersResponse response = new GetUsersResponse();
        List<User> users = userService.getUsersThatAreNotSharedWithAdminService(getUsersRequest.getUsername());
        List<UserXML> userXML = convertUsersToXML(users);
        response.setUser(userXML);

        return response;
    }

    private List<UserXML> convertUsersToXML(List<User> users) {
        List<UserXML> list = new ArrayList<>();
        users.forEach(user -> {
            UserXML userXML = new UserXML();
            userXML.setEmail(user.getEmail());
            userXML.setUsername(user.getUsername());
            userXML.setFirstName(user.getFirstName());
            userXML.setLastName(user.getLastName());
            userXML.setAddress(user.getAddress());
            userXML.setIsActive(user.isActive());
            userXML.setIsBlocked(user.isBlocked());

            list.add(userXML);
        });

        return list;
    }

}
