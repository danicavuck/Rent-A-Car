package com.group56.rentingservice.repository;

import com.group56.rentingservice.model.Advert;
import com.group56.rentingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long> {
   Advert findAdvertById(Long id);
   ArrayList<Advert> findAdvertsByPublisher(User user);
   Advert findAdvertByUuid(UUID uuid);

}
