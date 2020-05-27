package com.group56.authservice.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;


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
}
