package com.group56.UserService.repository;

import com.group56.UserService.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AdvertRepository extends JpaRepository<Advert, Long> {
    List<Advert> findAll();
}
