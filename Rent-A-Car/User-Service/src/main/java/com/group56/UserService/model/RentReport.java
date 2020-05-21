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
public class RentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String report;
    private Long kilometersDriven;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id")
    private Advert advert;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent agent;
}
