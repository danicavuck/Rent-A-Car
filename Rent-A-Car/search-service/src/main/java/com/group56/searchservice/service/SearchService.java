package com.group56.searchservice.service;

import com.group56.searchservice.DTO.*;
import com.group56.searchservice.model.Advert;
import com.group56.searchservice.repository.AdvertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
                .rentLimited(advert.getCar().isRentLimited())
                .limitInKilometers(advert.getCar().getLimitInKilometers())
                .numberOfSeatsForChildren(advert.getCar().getNumberOfSeatsForChildren())
                .uuid(advert.getUuid())
                .protectionAvailable(advert.isProtectionAvailable())
                .protectionPrice(advert.getProtectionPrice())
                .build();
    }

    public ResponseEntity<?> getAdvertsForSpecificUser(String username) {
        List<Advert> activeAdverts = advertRepository.findAdvertByIsActive(true);
        if(activeAdverts == null)
            return new ResponseEntity<>(null, HttpStatus.OK);

        List<Advert> userAdverts = activeAdverts.stream().filter(advert -> advert.getPublisher().equals(username)).collect(Collectors.toList());
        List<AdvertPreviewDTO> previewDTOS = new ArrayList<>();
        for (Advert advert: userAdverts) {
            previewDTOS.add(transformAdvertToPreviewDto(advert));
        }

        return new ResponseEntity<>(previewDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> advancedFilter(AdvancedQueryDTO advertDTO) {
        List<Advert> filteredList = advertRepository.findAll();

        if(!advertDTO.getCarLocation().equals("")){
            filteredList = filterByCarLocation(filteredList, advertDTO.getCarLocation());
        }

        if(advertDTO.getFrom() != null && advertDTO.getUntil() != null) {
            filteredList = filterByDate(filteredList, advertDTO.getFrom(), advertDTO.getUntil());
        }

        if(!advertDTO.getBrand().equals("")) {
            filteredList = filterByBrand(filteredList, advertDTO.getBrand());
        }

        if(!advertDTO.getModel().equals("")) {
            filteredList = filterByModel(filteredList, advertDTO.getModel());
        }

        if(!advertDTO.getFuelType().equals("")) {
            filteredList = filterByFuelType(filteredList, advertDTO.getFuelType());
        }

        if(!advertDTO.getTransmission().equals("")) {
            filteredList = filterByTransmission(filteredList, advertDTO.getTransmission());
        }

        if(advertDTO.getPriceMax() > advertDTO.getPriceMin() && advertDTO.getPriceMin() >= 0) {
            filteredList = filterByPrice(filteredList, advertDTO.getPriceMin(), advertDTO.getPriceMax());
        }

        if(advertDTO.getMileageMax() > advertDTO.getMileageMin() && advertDTO.getMileageMin() >= 0) {
            filteredList = filterByMileage(filteredList, advertDTO.getMileageMin(), advertDTO.getMileageMax());
        }

        if(advertDTO.getLimitInKilometers() > 0) {
            filteredList = filterByRentLimit(filteredList, advertDTO.getLimitInKilometers());
        }

        filteredList = filterByProtection(filteredList, advertDTO.isProtectionAvailable());

        if(advertDTO.getNumberOfSeatsForChildren() >= 0) {
            filteredList = filterByChildrenSeats(filteredList, advertDTO.getNumberOfSeatsForChildren());
        }

        return new ResponseEntity<>(transformListOfAdvertsToPreviewDto(filteredList), HttpStatus.OK);
    }

    private List<Advert> filterByCarLocation(List<Advert> filteredList, String location) {
        return filteredList.stream().filter(advert -> advert.getCarLocation().equals(location)).collect(Collectors.toList());
    }

    private List<Advert> filterByDate(List<Advert> filteredList, LocalDateTime from, LocalDateTime until) {
        return filteredList.stream()
                .filter(advert -> advert.getAvailableForRentFrom().isAfter(from)
                && advert.getAvailableForRentUntil().isBefore(until))
                .collect(Collectors.toList());
    }

    private List<Advert> filterByBrand(List<Advert> filteredList, String brand) {
        return filteredList.stream()
                .filter(advert -> advert.getCar().getCarBrand().getBrandName().equals(brand))
                .collect(Collectors.toList());
    }

    private List<Advert> filterByModel(List<Advert> filteredList, String model) {
        return filteredList.stream()
                .filter(advert -> advert.getCar().getCarModel().getModelName().equals(model))
                .collect(Collectors.toList());
    }

    private List<Advert> filterByFuelType(List<Advert> filteredList, String fuelType) {
        return filteredList.stream()
                .filter(advert -> advert.getCar().getFuelType().getFuelType().equals(fuelType))
                .collect(Collectors.toList());
    }

    private List<Advert> filterByTransmission(List<Advert> filteredList, String transmission) {
        return filteredList.stream()
                .filter(advert -> advert.getCar().getTransmissionType().getTransmissionType().equals(transmission))
                .collect(Collectors.toList());
    }

    private List<Advert> filterByPrice(List<Advert> filteredList, long priceMin, long priceMax) {
        return filteredList.stream()
                .filter(advert -> (advert.getPrice().compareTo(BigDecimal.valueOf(priceMin)) == 1 || advert.getPrice().compareTo(BigDecimal.valueOf(priceMin)) == 0)
                && (advert.getPrice().compareTo(BigDecimal.valueOf(priceMax)) == -1 || advert.getPrice().compareTo(BigDecimal.valueOf(priceMax)) == 0))
                .collect(Collectors.toList());
    }

    private List<Advert> filterByMileage(List<Advert> filteredList, long mileageMin, long mileageMax) {
        return filteredList.stream()
                .filter(advert -> advert.getCar().getMileage() >= mileageMin && advert.getCar().getMileage() <= mileageMax)
                .collect(Collectors.toList());
    }

    private List<Advert> filterByRentLimit(List<Advert> filteredList, long limitInKilometers) {
        return filteredList.stream()
                .filter(advert -> advert.getCar().getLimitInKilometers() <= limitInKilometers)
                .collect(Collectors.toList());
    }

    private List<Advert> filterByProtection(List<Advert> filteredList, boolean protectionAvailable) {
        return filteredList.stream()
                .filter(advert -> advert.isProtectionAvailable() == protectionAvailable)
                .collect(Collectors.toList());
    }

    private List<Advert> filterByChildrenSeats(List<Advert> filteredList, int numberOfSeatsForChildren) {
        return filteredList.stream()
                .filter(advert -> advert.getCar().getNumberOfSeatsForChildren() <= numberOfSeatsForChildren)
                .collect(Collectors.toList());
    }

    private List<AdvertPreviewDTO> transformListOfAdvertsToPreviewDto(List<Advert> filteredList) {
        List<AdvertPreviewDTO> dtos = new ArrayList<>();
        for(Advert advert : filteredList) {
            dtos.add(transformAdvertToPreviewDto(advert));
        }

        return dtos;
    }

    public ResponseEntity<?> sortAdverts(AdvertSortDTO advertSortDTO) {
        List<AdvertPreviewDTO> adverts = advertSortDTO.getAdverts();

        switch (advertSortDTO.getCriteria()) {
            case "priceAsc" : adverts = sortAdvertsByPriceAscending(adverts);
                break;
            case "priceDesc" : adverts = sortAdvertsByPriceDescending(adverts);
                break;
            case "mileageAsc" : adverts = sortAdvertsByMileageAscending(adverts);
                break;
            case "mileageDesc" : adverts = sortAdvertsByMileageDescending(adverts);
                break;
            case "marksAsc" : adverts = sortAdvertsByMarksAscending(adverts);
                break;
            case "marksDesc" : adverts = sortAdvertsByMarksDescending(adverts);
                break;
            default: return new ResponseEntity<>("Unfamiliar criteria", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(adverts, HttpStatus.OK);
    }

    private List<AdvertPreviewDTO> sortAdvertsByPriceAscending(List<AdvertPreviewDTO> adverts) {
        return adverts.stream()
                .sorted(Comparator.comparing(AdvertPreviewDTO::getPrice))
                .collect(Collectors.toList());
    }

    private List<AdvertPreviewDTO> sortAdvertsByPriceDescending(List<AdvertPreviewDTO> adverts) {
        return adverts.stream()
                .sorted(Comparator.comparing(AdvertPreviewDTO::getPrice).reversed())
                .collect(Collectors.toList());
    }

    private List<AdvertPreviewDTO> sortAdvertsByMileageAscending(List<AdvertPreviewDTO> adverts) {
        return adverts.stream()
                .sorted(Comparator.comparing(AdvertPreviewDTO::getMileage))
                .collect(Collectors.toList());
    }

    private List<AdvertPreviewDTO> sortAdvertsByMileageDescending(List<AdvertPreviewDTO> adverts) {
        return adverts.stream()
                .sorted(Comparator.comparing(AdvertPreviewDTO::getMileage).reversed())
                .collect(Collectors.toList());
    }

    private List<AdvertPreviewDTO> sortAdvertsByMarksAscending(List<AdvertPreviewDTO> adverts) {
        return adverts.stream()
                .sorted(Comparator.comparing(AdvertPreviewDTO::getCarLocation))
                .collect(Collectors.toList());
    }
    private List<AdvertPreviewDTO> sortAdvertsByMarksDescending(List<AdvertPreviewDTO> adverts) {
        return adverts.stream()
                .sorted(Comparator.comparing(AdvertPreviewDTO::getCarLocation).reversed())
                .collect(Collectors.toList());
    }
}
