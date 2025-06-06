package com.example.Aisian.Cuisine.repository;

import com.example.Aisian.Cuisine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);


    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
