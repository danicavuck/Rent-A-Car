package com.group56.postingservice.repository;

import com.group56.postingservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdvertRepository extends JpaRepository<Advert,Long> {
    Advert findAdvertById(Long id);
    Advert findAdvertByUuid(UUID uuid);
}
