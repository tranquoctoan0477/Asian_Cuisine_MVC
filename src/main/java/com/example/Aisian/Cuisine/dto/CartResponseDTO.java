package com.example.Aisian.Cuisine.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponseDTO {
    private List<CartItemResponseDTO> items;
    private BigDecimal total;

    public CartResponseDTO(List<CartItemResponseDTO> items, BigDecimal total) {
        this.items = items;
        this.total = total;
    }

    public List<CartItemResponseDTO> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
