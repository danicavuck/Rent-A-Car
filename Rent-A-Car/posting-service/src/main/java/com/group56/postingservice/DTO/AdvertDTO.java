package com.group56.postingservice.DTO;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdvertDTO {
    private String carLocation;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    //-------------car---
    private Long id_carBrand;
    private Long id_carModel;
    private Long id_fuelType;
    private Long id_transmissionType;
    private Long id_bodyType;
    private Long mileage;
    private boolean isRentLimited;
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;
}
