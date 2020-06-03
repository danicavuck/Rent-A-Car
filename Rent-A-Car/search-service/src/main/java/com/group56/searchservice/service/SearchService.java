package com.group56.searchservice.service;

import com.group56.searchservice.DTO.AdvertQueryDTO;
import com.group56.searchservice.model.Advert;
import com.group56.searchservice.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private AdvertRepository advertRepository;

    @Autowired
    public SearchService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }


    public ResponseEntity<?> findRelevantAdverts(AdvertQueryDTO advertDTO) {
        if(isQueryValid(advertDTO)) {
            ArrayList<Advert> advertList = advertRepository.findAll();
            List relevantAdverts = advertList.stream()
                    .filter(advert -> (advert.getCarLocation().equals(advertDTO.getCarLocation())))
                    .filter(advert -> advert.getAvailableForRentFrom().isBefore(advertDTO.getRentStart()) && advert.getAvailableForRentUntil().isAfter(advertDTO.getRentEnd()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(relevantAdverts, HttpStatus.OK);
        }
        return new ResponseEntity<>("Query is not valid", HttpStatus.BAD_REQUEST);
    }

    private boolean isQueryValid(AdvertQueryDTO advert) {
        return (advert.getCurrentTime().isBefore(advert.getRentStart().plusHours(48))
                && advert.getRentStart().isBefore(advert.getRentEnd())
                && advert.getCarLocation().length() > 0);

    }

    public ResponseEntity<?> findAdvertById(Long id) {
        Advert advert = advertRepository.findAdvertById(id);
        if(advert != null)
            return new ResponseEntity<>(advert, HttpStatus.OK);
        return new ResponseEntity<>("Advert not found", HttpStatus.NOT_FOUND);
    }

}
