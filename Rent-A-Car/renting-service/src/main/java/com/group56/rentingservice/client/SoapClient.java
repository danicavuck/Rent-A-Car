package com.group56.rentingservice.client;

import com.group56.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class SoapClient {
    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public GetUsersResponse getUsers(GetUsersRequest request) {
        template = new WebServiceTemplate(marshaller);
        return (GetUsersResponse) template.marshalSendAndReceive("http://localhost:8080/auth-service/soapWS", request);
    }

    public GetAdminsResponse getAdmins(GetAdminsRequest request) {
        template = new WebServiceTemplate(marshaller);
        return (GetAdminsResponse) template.marshalSendAndReceive("http://localhost:8080/auth-service/soapWS", request);
    }

    public GetAgentsResponse getAgents(GetAgentsRequest request) {
        template = new WebServiceTemplate(marshaller);
        return (GetAgentsResponse) template.marshalSendAndReceive("http://localhost:8080/auth-service/soapWS", request);
    }
}
