package com.group56.searchservice.DTO;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdvancedQueryDTO {
    @Id
    private long id;
    private String carLocation;
    private String brand;
    private String model;
    private String transmission;
    private String bodyType;
    private String fuelType;
    private long priceMin;
    private long priceMax;
    private long mileageMin;
    private long mileageMax;
    private long limitInKilometers;
    private boolean protectionAvailable;
    private int numberOfSeatsForChildren;
    private LocalDateTime from;
    private LocalDateTime until;
}
