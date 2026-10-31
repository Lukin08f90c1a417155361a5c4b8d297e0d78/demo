package com.example.lostfound.repository;

import com.example.lostfound.model.ItemClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemClaimRepository extends JpaRepository<ItemClaim, Long> {}