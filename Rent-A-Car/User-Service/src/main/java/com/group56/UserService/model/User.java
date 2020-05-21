package com.group56.UserService.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String registrationNumber;
    private boolean isActive = true;
    private boolean isBlocked = false;
    private boolean isAllowedToRent = true;
    private int numberOfCancelledReservations;
    @OneToMany(mappedBy = "publisher")
    private List<Advert> advertsPublished = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Mark> userMarks = new ArrayList<>();
    @OneToMany(mappedBy = "publisher")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<RentRequest> rentRequests = new ArrayList<>();
}
