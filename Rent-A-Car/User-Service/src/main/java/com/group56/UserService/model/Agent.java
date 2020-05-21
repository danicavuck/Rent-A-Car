package com.group56.UserService.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String agentName;
    private String password;
    private String address;
    private String registrationNumber;
    @OneToOne(mappedBy = "agent", fetch = FetchType.LAZY)
    private RentReport rentReport;
}
