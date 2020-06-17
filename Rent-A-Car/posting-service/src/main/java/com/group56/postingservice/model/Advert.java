package com.group56.postingservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String carLocation;
    private LocalDateTime rentFrom;
    private LocalDateTime rentUntil;
    private boolean isProtectionAvailable;
    private BigDecimal protectionPrice;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private User publisher;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Car car;

    @OneToMany(mappedBy = "advert")
    private List<Mark> marks = new ArrayList<>();

    @OneToMany(mappedBy = "advert")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    private List<RentRequest> rentRequests = new ArrayList<>();
}
