package com.group56.postingservice.repository;

import com.group56.postingservice.model.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType,Long> {
    BodyType findBodyTypeById(Long id);
}
