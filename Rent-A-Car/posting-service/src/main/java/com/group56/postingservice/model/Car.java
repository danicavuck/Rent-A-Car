package com.group56.postingservice.model;

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
    private Long limitInKilometers;
    private int numberOfSeatsForChildren;
    @ManyToOne
    private BodyType bodyType;
    @ManyToOne
    private FuelType fuelType;
    @ManyToOne
    private TransmissionType transmissionType;
    @ManyToOne
    private CarBrand carBrand;
    @ManyToOne
    private CarModel carModel;

    @OneToOne(mappedBy = "car")
    private Advert advert;
}