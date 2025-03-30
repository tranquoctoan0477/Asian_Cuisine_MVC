package com.example.Aisian.Cuisine.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDTO {
    private Long id;
    private String status;
    private BigDecimal total;
    private LocalDate createdAt;

    // Constructor
    public OrderDTO(Long id, String status, BigDecimal total, LocalDate createdAt) {
        this.id = id;
        this.status = status;
        this.total = total;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
