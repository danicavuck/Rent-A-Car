package com.group56.rentingservice.model;

import lombok.*;
import com.group56.rentingservice.model.User;
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
    private String description;
    private String carLocation;
    private LocalDateTime rentFrom;
    private LocalDateTime rentUntil;
    private boolean isProtectionAvailable;
    private BigDecimal protectionPrice;
    private BigDecimal price;
    private boolean isActive;
    private boolean isSharedWithReviewService;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private User publisher;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Car car;


    @ManyToMany
    private List<RentRequest> rentRequests = new ArrayList<>();
}
