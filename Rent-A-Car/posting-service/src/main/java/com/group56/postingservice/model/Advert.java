package com.group56.postingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
public class Advert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String description;
    private String carLocation;
    private LocalDateTime rentFrom;
    private LocalDateTime rentUntil;
    private boolean isProtectionAvailable;
    private BigDecimal protectionPrice;
    private BigDecimal price;
    private boolean isActive;
    private boolean isSharedWithReviewService;
    private boolean isSharedWithSearchService;
    private boolean isSharedWithRentService;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private User publisher;

    @OneToOne(fetch = FetchType.LAZY)
    private Car car;

    @OneToMany(mappedBy = "advert")
    private List<Mark> marks = new ArrayList<>();

    @OneToMany(mappedBy = "advert")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    private List<RentRequest> rentRequests = new ArrayList<>();
}
