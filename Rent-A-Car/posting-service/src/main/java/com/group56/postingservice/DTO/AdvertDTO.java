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
    private String username;
    private String carLocation;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    //-------------car---
    private String brand;
    private String model;
    private String fuel;
    private String transmission;
    private String bodyType;
    private Long mileage;
    private boolean isRentLimited;
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;
}
