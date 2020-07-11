package com.group56.postingservice.model;

import lombok.*;

import javax.persistence.*;
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
public class RentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean active;
    @Column
    private boolean bundle;
    @Column
    private UUID uuid;

    //user that made the request
    @Column
    private String requestUsername;
    //advert creator
    @Column
    private String publisherUsername;

    @Column
    private boolean accepted;

    @ManyToMany
    private List<Advert> advertList = new ArrayList<>();


    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    private LocalDateTime timeAccepted;
}