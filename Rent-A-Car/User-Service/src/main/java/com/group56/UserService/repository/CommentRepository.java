package com.group56.UserService.repository;

import com.group56.UserService.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAll();
}
