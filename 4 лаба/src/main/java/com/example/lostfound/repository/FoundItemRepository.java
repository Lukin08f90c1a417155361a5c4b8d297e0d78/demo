package com.example.lostfound.repository;

import com.example.lostfound.model.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {}