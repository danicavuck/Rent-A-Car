package com.group56.searchservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"advert"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long mileage;
    private boolean isRentLimited;
    private boolean isAvailableForRenting;
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;

    @ManyToOne(fetch = FetchType.LAZY)
    private CarBrand carBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    private CarModel carModel;

    @ManyToOne
    private BodyType bodyType;

    @ManyToOne(fetch = FetchType.LAZY)
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.LAZY)
    private TransmissionType transmissionType;

    @OneToOne(mappedBy = "car")
    private Advert advert;
}

