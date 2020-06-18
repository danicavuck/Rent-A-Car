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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private BodyType bodyType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private FuelType fuelType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private TransmissionType transmissionType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private CarBrand carBrand;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private CarModel carModel;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private Advert advert;
}