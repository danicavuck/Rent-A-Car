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
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private boolean isActive;
    private boolean isBlocked;
    private boolean isSharedWithAdmin;
    private boolean isSharedWithRenting;
}
