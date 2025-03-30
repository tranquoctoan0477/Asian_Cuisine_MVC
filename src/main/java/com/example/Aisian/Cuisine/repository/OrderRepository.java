package com.example.Aisian.Cuisine.repository;

import com.example.Aisian.Cuisine.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserIdAndStatus(Long userId, String status);
    List<Order> findByUserId(Long userId);
}
