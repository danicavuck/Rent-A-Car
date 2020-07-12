package com.group56.postingservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.group56.postingservice.model.Car;
import com.group56.postingservice.model.Comment;
import com.group56.postingservice.model.Mark;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
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
    private String uuid;
    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private Car car;

    @OneToMany(mappedBy = "advert")
    private List<Mark> marks = new ArrayList<>();

    @OneToMany(mappedBy = "advert")
    private List<Comment> comments = new ArrayList<>();
}
