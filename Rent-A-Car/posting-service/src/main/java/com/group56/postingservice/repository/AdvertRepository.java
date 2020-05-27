package com.group56.postingservice.repository;

import com.group56.postingservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert,Long> {
}
