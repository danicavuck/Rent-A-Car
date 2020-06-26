package com.group56.postingservice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mark implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int mark;
    @ManyToOne
    private User user;
    @ManyToOne
    private Advert advert;
    @OneToOne
    private Comment comment;
}