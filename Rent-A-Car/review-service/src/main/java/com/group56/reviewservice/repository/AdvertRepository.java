package com.group56.reviewservice.repository;

import com.group56.reviewservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Advert findAdvertByUuid(UUID uuid);
}
