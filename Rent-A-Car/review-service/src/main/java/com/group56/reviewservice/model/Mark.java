package com.group56.reviewservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int mark;
    @ManyToOne
    private User user;
    @ManyToOne
    private Advert advert;
}
