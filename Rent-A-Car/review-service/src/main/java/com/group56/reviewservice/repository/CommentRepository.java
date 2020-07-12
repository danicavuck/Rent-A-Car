package com.group56.reviewservice.repository;

import com.group56.reviewservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByUuid(String uuid);
}
