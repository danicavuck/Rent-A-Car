package com.group56.postingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"advert"})
public class Car implements Serializable {
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