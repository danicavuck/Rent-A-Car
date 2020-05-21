package com.group56.UserService.repository;

import com.group56.UserService.model.RentReport;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RentReportCatalogue extends JpaRepository<RentReport, Long> {
    List<RentReport> findAll();
}
