package com.example.lostfound.repository;

import com.example.lostfound.model.LossReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LossReportRepository extends JpaRepository<LossReport, Long> {}