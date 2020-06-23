package com.group56.SOAPEndpoint;

import com.group56.postingservice.model.Advert;
import com.group56.postingservice.service.AdvertService;
import com.group56.soap_package.AdvertXML;
import com.group56.soap_package.GetAdvertsRequest;
import com.group56.soap_package.GetAdvertsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class AdvertEndpoint {
    private AdvertService advertService;

    @Autowired
    public AdvertEndpoint(AdvertService advertService) {
        this.advertService = advertService;
    }

    @PayloadRoot(namespace = "http://group56.com/soap-package", localPart = "getAdvertsRequest")
    @ResponsePayload
    public GetAdvertsResponse getAdvertsResponse(GetAdvertsRequest request) {
        GetAdvertsResponse response = new GetAdvertsResponse();
        List<Advert> newAdverts = advertService.getNewlyCreatedAdverts();
        response.setAdverts(createXMLFromModel(newAdverts));
        return response;
    }

    private List<AdvertXML> createXMLFromModel(List<Advert> newAdverts) {
        List<AdvertXML> advertsXML = new ArrayList<>();

        newAdverts.forEach(advert ->  {
            AdvertXML advertXML = new AdvertXML();
            advertXML.setIsActive(advert.isActive());
            advertXML.setUuid(advert.getUuid().toString());

            advertsXML.add(advertXML);
        });

        return advertsXML;
    }
}
