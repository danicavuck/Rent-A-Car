package com.group56.UserService.model;

import com.group56.UserService.enumeration.BodyType;
import com.group56.UserService.enumeration.FuelType;
import com.group56.UserService.model.TransmissionType;
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

    @ManyToOne
    @JoinColumn(name = "carBrand_id")
    private CarBrand carBrand;
    @ManyToOne
    @JoinColumn(name = "carModel_id")
    private CarModel carModel;
    @ManyToOne
    @JoinColumn(name = "carClass_id")
    private CarClass carClass;

    private Long mileage;
    private boolean isRentLimited;
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;

    //ovo je klasa auta valjda
    //private BodyType bodyType;

    @ManyToOne
    @JoinColumn(name = "typeOfFuel_id")
    private TypeOfFuel typeOfFuel;

    @ManyToOne
    @JoinColumn(name = "transmissionType_id")
    private TransmissionType transmissionType;

    @OneToOne(mappedBy = "car")
    private Advert advert;
}
