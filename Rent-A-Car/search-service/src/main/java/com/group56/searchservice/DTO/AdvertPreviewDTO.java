package com.group56.searchservice.DTO;

import lombok.*;

import javax.persistence.Entity;
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
public class AdvertPreviewDTO {
    @Id
    private long id;
    private String publisher;
    private BigDecimal price;
    private String carLocation;
    private LocalDateTime availableForRentFrom;
    private LocalDateTime availableForRentUntil;
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
    private boolean protectionAvailable;
    private BigDecimal protectionPrice;
    private String description;
}
