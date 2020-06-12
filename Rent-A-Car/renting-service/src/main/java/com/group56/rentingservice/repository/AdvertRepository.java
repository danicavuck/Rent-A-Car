package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long> {
   Advert findAdvertById(Long id);

}
