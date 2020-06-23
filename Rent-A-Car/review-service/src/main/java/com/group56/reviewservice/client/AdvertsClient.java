package com.group56.reviewservice.client;

import com.group56.soap.GetAdvertsRequest;
import com.group56.soap.GetAdvertsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class AdvertsClient {
    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public GetAdvertsResponse getAdverts(GetAdvertsRequest request) {
        template = new WebServiceTemplate(marshaller);
        return (GetAdvertsResponse) template.marshalSendAndReceive("http://localhost:8080/posting-service/soapWS", request);
    }
}
