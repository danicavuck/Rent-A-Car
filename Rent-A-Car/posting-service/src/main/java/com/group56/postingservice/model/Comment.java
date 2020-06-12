package com.group56.postingservice.model;
import com.group56.postingservice.util.CommentStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Comment {
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