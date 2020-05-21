package com.group56.UserService.model;

import com.group56.UserService.enumeration.BodyType;
import com.group56.UserService.enumeration.FuelType;
import com.group56.UserService.enumeration.TransmissionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private Long mileage;
    private boolean isRentLimited;
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;
    private BodyType bodyType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    @OneToOne(mappedBy = "car")
    private Advert advert;
}
