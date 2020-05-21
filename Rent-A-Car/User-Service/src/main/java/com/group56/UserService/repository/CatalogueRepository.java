package com.group56.UserService.repository;

import com.group56.UserService.model.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {
    List<Catalogue> findAll();
}
