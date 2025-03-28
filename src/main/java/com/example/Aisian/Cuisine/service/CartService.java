package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.dto.CartRequestDTO;
import com.example.Aisian.Cuisine.dto.CartResponseDTO;
import com.example.Aisian.Cuisine.dto.CartUpdateRequestDTO;

public interface CartService {

    void addToCart(Long userId, CartRequestDTO request);
    void updateCart(Long userId, CartUpdateRequestDTO request);
    void removeItem(Long userId, Long productId);
    CartResponseDTO getCart(Long userId);


}
