package com.group56.searchservice.model;

import lombok.*;

import javax.persistence.*;

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
    private Long mileage;
    private boolean isRentLimited;
    private boolean isAvailableForRenting;
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_brand")
    private CarBrand carBrand;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model")
    private CarModel carModel;
    @ManyToOne
    @JoinColumn(name = "body_type")
    private BodyType bodyType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_type")
    private FuelType fuelType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transmission_type")
    private TransmissionType transmissionType;
    @OneToOne(mappedBy = "car")
    private Advert advert;
}

