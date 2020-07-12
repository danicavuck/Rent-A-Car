package com.group56.reviewservice.service;

import com.group56.reviewservice.DTO.CommentDTO;
import com.group56.reviewservice.enumertation.CommentStatus;
import com.group56.reviewservice.model.Advert;
import com.group56.reviewservice.model.Comment;
import com.group56.reviewservice.model.User;
import com.group56.reviewservice.repository.AdvertRepository;
import com.group56.reviewservice.repository.CommentRepository;
import com.group56.reviewservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private AdvertRepository advertRepository;
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    public ReviewService(CommentRepository commentRepository, AdvertRepository advertRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> postANewComment(CommentDTO commentDTO) {
        Comment comment = formModelOutOfDTO(commentDTO);
        commentRepository.save(comment);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    private Comment formModelOutOfDTO(CommentDTO commentDTO) {
        Advert advert = advertRepository.findAdvertByUuid(UUID.fromString(commentDTO.getUuid()));
        User user = userRepository.findUserByUsername(commentDTO.getUsername());
        if(user == null) {
            logger.error("User with username " + commentDTO.getUsername() + " not found");
        }

        Comment comment = Comment.builder()
                .uuid(UUID.randomUUID().toString())
                .text(commentDTO.getText())
                .advert(advert)
                .username(commentDTO.getUsername())
                .mark(commentDTO.getMark())
                .commentStatus(CommentStatus.PENDING)
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

        return new ResponseEntity<>(formDtosOutOfComments(pending), HttpStatus.OK);
    }

    private List<CommentDTO> formDtosOutOfComments(List<Comment> pending) {
        List<CommentDTO> dtos = new ArrayList<>();

        pending.forEach(comment -> {
            CommentDTO dto = CommentDTO.builder()
                    .text(comment.getText())
                    .username(comment.getUsername())
                    .mark(comment.getMark())
                    .uuid(comment.getUuid().toString())
                    .build();

            dtos.add(dto);
        });

        return dtos;
    }

    public ResponseEntity<?> getCommentsForAdvert(UUID uuid) {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> relevantComments = new ArrayList<>();

        comments.forEach(comment -> {
            if(comment.getCommentStatus().equals(CommentStatus.APPROVED)) {
                relevantComments.add(comment);
            }
        });

        return new ResponseEntity<>(relevantComments, HttpStatus.OK);
    }

    public ResponseEntity<?> approveComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.findCommentByUuid(commentDTO.getUuid());
        if(comment != null) {
            comment.setCommentStatus(CommentStatus.APPROVED);
            commentRepository.save(comment);
            return new ResponseEntity<>("Comment is approved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment is not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> declineComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.findCommentByUuid(commentDTO.getUuid());
        if(comment != null) {
            comment.setCommentStatus(CommentStatus.DECLINED);
            commentRepository.save(comment);
            return new ResponseEntity<>("Comment is declined", HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment is not found", HttpStatus.NOT_FOUND);
    }

}
