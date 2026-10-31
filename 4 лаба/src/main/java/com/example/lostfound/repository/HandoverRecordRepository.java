package com.example.lostfound.repository;

import com.example.lostfound.model.HandoverRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandoverRecordRepository extends JpaRepository<HandoverRecord, Long> {}