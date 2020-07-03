package com.group56.searchservice.model;

import com.group56.searchservice.enumeration.CommentStatus;
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
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User publisher;
    @ManyToOne
    private Advert advert;
    private CommentStatus commentStatus;
}
