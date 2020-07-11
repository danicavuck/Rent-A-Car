package com.group56.searchservice.DTO;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdvertDTO {
    @Id
    private long id;
    private String description;
    private BigDecimal price;
    private String username;
    private String carLocation;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private boolean protectionAvailable;
    private BigDecimal protectionPrice;
    //-------------car---
    private String brand;
    private String model;
    private String fuel;
    private String transmission;
    private String bodyType;
    private Long mileage;
    private boolean rentLimited;
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;
    private String uuid;
}
