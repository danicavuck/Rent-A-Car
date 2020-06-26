package com.group56.searchservice.service;

import com.group56.searchservice.model.Advert;
import com.group56.searchservice.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdvertService {
    private AdvertRepository advertRepository;

    @Autowired
    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    public void fetchNewAdverts() {
        RestTemplate restTemplate = new RestTemplate();
        String apiEndpoint = "http://localhost:8080/posting-service/advert/search-service";
        ResponseEntity<Advert[]> response = restTemplate.getForEntity(apiEndpoint, Advert[].class);
        Advert[] adverts = response.getBody();

        for(int i = 0; i< adverts.length; i++) {
            advertRepository.save(adverts[i]);
        }
    }
}
