package com.group56.postingservice.model;
import com.group56.postingservice.util.CommentStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User publisher;
    @ManyToOne
    private Advert advert;
    @OneToOne
    private Mark mark;
    private CommentStatus commentStatus;
}