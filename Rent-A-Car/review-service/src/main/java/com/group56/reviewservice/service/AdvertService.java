package com.group56.reviewservice.service;

import com.group56.reviewservice.client.AdvertsClient;
import com.group56.reviewservice.client.SOAPClient;
import com.group56.reviewservice.model.Advert;
import com.group56.reviewservice.repository.AdvertRepository;
import com.group56.soap.AdvertXML;
import com.group56.soap.GetAdvertsRequest;
import com.group56.soap.GetAdvertsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertService {
    private AdvertRepository advertRepository;
    private AdvertsClient advertsClient;

    @Autowired
    public AdvertService(AdvertRepository advertRepository, AdvertsClient advertsClient) {
        this.advertRepository = advertRepository;
        this.advertsClient = advertsClient;
    }


    public List<AdvertXML> getAdvertsXMLFromSoapRequest() {
        GetAdvertsRequest request = new GetAdvertsRequest();
        request.setAdvertUUID("");
        GetAdvertsResponse response = advertsClient.getAdverts(request);
        return response.getAdverts();
    }

    public void saveAdverts(List<Advert> adverts) {
        adverts.forEach(advert -> {
            this.advertRepository.save(advert);
        });
    }
}
