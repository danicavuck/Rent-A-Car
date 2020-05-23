package com.group56.UserService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group56.UserService.model.Car;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "carBrand")
    private Set<Car> cars;


}
