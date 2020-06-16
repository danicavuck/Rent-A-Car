package com.group56.reviewservice.controller;

import com.group56.reviewservice.DTO.CommentDTO;
import com.group56.reviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/review-service/comment")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> postANewComment(@RequestBody CommentDTO commentDTO) {
        return reviewService.postANewComment(commentDTO);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingCommments() {
        return reviewService.getAllPendingComments();
    }

    @GetMapping("/{advertUUID}")
    public ResponseEntity<?> getCommentsForAdvert(@PathVariable("advertUUID") UUID uuid) {
        return reviewService.getCommnetsForAdvert(uuid);
    }
}
