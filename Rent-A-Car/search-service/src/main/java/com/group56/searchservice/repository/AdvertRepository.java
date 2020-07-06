package com.group56.searchservice.repository;

import com.group56.searchservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Advert findAdvertByUuid(String id);
    List<Advert> findAdvertByIsActive(boolean active);
    List<Advert> findAdvertByCarLocation(String carLocation);
}
