package com.group56.rentingservice.model;

import com.group56.rentingservice.util.RentRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class RentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean active;
    @Column
    private boolean bundle;

    //user that made the request
    @Column
    private Long userId;
    //advert creator
    @Column
    private Long publisherId;
    @Column
    private boolean accepted;

    @ManyToMany
    private List<Advert> advertList = new ArrayList<>();

    private RentRequestStatus rentRequestStatus;

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    private LocalDateTime timeAccepted;
}
