package com.group56.reviewservice.service;

import com.group56.reviewservice.DTO.CommentDTO;
import com.group56.reviewservice.enumertation.CommentStatus;
import com.group56.reviewservice.model.Advert;
import com.group56.reviewservice.model.Comment;
import com.group56.reviewservice.model.User;
import com.group56.reviewservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private CommentRepository commentRepository;

    @Autowired
    public ReviewService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public ResponseEntity<?> postANewComment(CommentDTO commentDTO) {
        Comment comment = formModelOutOfDTO(commentDTO);
        commentRepository.save(comment);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    private Comment formModelOutOfDTO(CommentDTO commentDTO) {
        Advert advert = Advert.builder().UUID(commentDTO.getUUID()).build();
        User user = User.builder().username(commentDTO.getUsername()).build();
        Comment comment = Comment.builder()
                .uuid(UUID.randomUUID())
                .text(commentDTO.getText())
                .advert(advert)
                .user(user)
                .build();

        return comment;
    }

    public ResponseEntity<?> getAllPendingComments() {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> pending = new ArrayList<>();

        comments.forEach(comment -> {
            if(comment.getCommentStatus().equals(CommentStatus.PENDING))
                pending.add(comment);
        });

        return new ResponseEntity<>(pending, HttpStatus.OK);
    }

    public ResponseEntity<?> getCommnetsForAdvert(UUID uuid) {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> relevantComments = new ArrayList<>();

        comments.forEach(comment -> {
            if(comment.getAdvert().getUUID().equals(uuid))
                relevantComments.add(comment);
        });

        return new ResponseEntity<>(relevantComments, HttpStatus.OK);
    }
}
