package com.group56.postingservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.group56.postingservice.model.Car;
import com.group56.postingservice.model.Comment;
import com.group56.postingservice.model.Mark;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"car"})
public class AdvertSearchDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carLocation;
    private LocalDateTime rentFrom;
    private LocalDateTime rentUntil;
    private LocalDateTime availableForRentFrom;
    private LocalDateTime availableForRentUntil;
    private boolean isProtectionAvailable;
    private BigDecimal protectionPrice;
    private BigDecimal price;
    private String publisher;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "advert")
    private List<Mark> marks = new ArrayList<>();

    @OneToMany(mappedBy = "advert")
    private List<Comment> comments = new ArrayList<>();
}
