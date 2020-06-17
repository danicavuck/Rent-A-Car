package com.group56.reviewservice.client;

import com.group56.soap.GetUsersRequest;
import com.group56.soap.GetUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class SOAPClient {
    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public GetUsersResponse getUsers(GetUsersRequest request) {
        template = new WebServiceTemplate(marshaller);
        return (GetUsersResponse) template.marshalSendAndReceive("http://localhost:8080/auth-service/soapWS", request);
    }
}
