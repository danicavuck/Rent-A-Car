package com.group56.reviewservice.repository;

import com.group56.reviewservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
}
