package com.group56.rentingservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FuelType implements Serializable {
    @Id
    private Long id;
    private String fuelType;
}
