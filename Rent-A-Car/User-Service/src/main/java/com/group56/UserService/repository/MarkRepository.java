package com.group56.UserService.repository;

import com.group56.UserService.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAll();
}
