package com.group56.searchservice.service;

import com.group56.searchservice.DTO.AdvertFilterDTO;
import com.group56.searchservice.DTO.AdvertPreviewDTO;
import com.group56.searchservice.DTO.AdvertQueryDTO;
import com.group56.searchservice.model.Advert;
import com.group56.searchservice.repository.AdvertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private Logger logger = LoggerFactory.getLogger(SearchService.class);
    private AdvertRepository advertRepository;

    @Autowired
    public SearchService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }


    public ResponseEntity<?> findRelevantAdverts(AdvertQueryDTO advertDTO) {
        if(isQueryValid(advertDTO)) {
            List<Advert> advertList = advertRepository.findAll();
            List relevantAdverts = advertList.stream()
                    .filter(advert -> (advert.getCarLocation().equals(advertDTO.getCarLocation())))
                    .filter(advert -> advert.getAvailableForRentFrom().isBefore(advertDTO.getRentStart())
                            && advert.getAvailableForRentUntil().isAfter(advertDTO.getRentEnd()))
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

    public ResponseEntity<?> findAdvertByUUID(String uuid) {
        logger.info("Finding advert with uuid: ", uuid);
        Advert advert = advertRepository.findAdvertByUuid(uuid);
        if(advert != null) {
            AdvertPreviewDTO advertPreviewDTO = transformAdvertToPreviewDto(advert);
            List<AdvertPreviewDTO> listOfAdvertDTOs = new ArrayList<>();
            listOfAdvertDTOs.add(advertPreviewDTO);
            return new ResponseEntity<>(listOfAdvertDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>("Advert not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> filterAdverts(AdvertFilterDTO advertQueryDTO) {
        if(advertQueryDTO.getCity().equals("") && advertQueryDTO.getFrom() == advertQueryDTO.getUntil())
            return new ResponseEntity<>("Invalid query", HttpStatus.NOT_FOUND);

        List<Advert> adverts;
        if(!advertQueryDTO.getCity().equals("")) {
            adverts = advertRepository.findAdvertByCarLocation(advertQueryDTO.getCity());
        } else {
            adverts = advertRepository.findAll();
        }
        List<Advert> filteredAdverts = filteredByDate(advertQueryDTO, adverts);

        List<AdvertPreviewDTO> advertsDTO = new ArrayList<>();
        for(Advert advert : filteredAdverts) {
            AdvertPreviewDTO dto = transformAdvertToPreviewDto(advert);
            advertsDTO.add(dto);
        }
        return new ResponseEntity<>(advertsDTO, HttpStatus.OK);
    }

    private List<Advert> filteredByDate(AdvertFilterDTO advertDTO, List<Advert> adverts) {
        if(advertDTO.getFrom() == advertDTO.getUntil() || advertDTO.getFrom() == null || advertDTO.getUntil() == null || adverts == null)
            return adverts;

        return adverts.stream()
                .filter(data -> data.getAvailableForRentFrom().isAfter(advertDTO.getFrom())
                        && data.getAvailableForRentUntil().isBefore(advertDTO.getUntil()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> getActiveAdverts() {
        List<Advert> adverts = advertRepository.findAdvertByIsActive(true);
        List<AdvertPreviewDTO> advertDTOs = new ArrayList<>();
        for(Advert advert : adverts) {
            AdvertPreviewDTO dto = transformAdvertToPreviewDto(advert);
            advertDTOs.add(dto);
        }
        return new ResponseEntity<>(advertDTOs, HttpStatus.OK);
    }

    private AdvertPreviewDTO transformAdvertToPreviewDto(Advert advert) {
        return AdvertPreviewDTO.builder()
                .publisher(advert.getPublisher())
                .price(advert.getPrice())
                .carLocation(advert.getCarLocation())
                .availableForRentFrom(advert.getAvailableForRentFrom())
                .availableForRentUntil(advert.getAvailableForRentUntil())
                .brand(advert.getCar().getCarBrand().getBrandName())
                .model(advert.getCar().getCarModel().getModelName())
                .fuel(advert.getCar().getFuelType().getFuelType())
                .transmission(advert.getCar().getTransmissionType().getTransmissionType())
                .bodyType(advert.getCar().getBodyType().getBodyType())
                .mileage(advert.getCar().getMileage())
                .isRentLimited(advert.getCar().isRentLimited())
                .limitInKilometers(advert.getCar().getLimitInKilometers())
                .numberOfSeatsForChildren(advert.getCar().getNumberOfSeatsForChildren())
                .uuid(advert.getUuid())
                .build();
    }
}
