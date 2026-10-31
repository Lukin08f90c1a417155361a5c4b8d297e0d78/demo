package com.example.lostfound.repository;

import com.example.lostfound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}