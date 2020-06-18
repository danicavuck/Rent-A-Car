package com.group56.reviewservice.model;

import lombok.*;

import javax.persistence.*;
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
    @OneToMany(mappedBy = "user")
    private List<Mark> marks;
    private boolean isActive = true;
    private boolean isBlocked = false;

}
