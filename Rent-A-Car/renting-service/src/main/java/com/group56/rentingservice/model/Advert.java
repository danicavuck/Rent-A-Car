package com.group56.rentingservice.model;

import lombok.*;
import com.group56.rentingservice.model.User;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String carLocation;
    private LocalDateTime rentFrom;
    private LocalDateTime rentUntil;

    @ManyToOne
    private User publisher;
    @OneToOne
    private Car car;
    @ManyToMany
    private List<RentRequest> rentRequests = new ArrayList<>();
}
