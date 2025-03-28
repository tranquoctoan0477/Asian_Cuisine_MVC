package com.example.Aisian.Cuisine.controller;

import com.example.Aisian.Cuisine.dto.CartRequestDTO;
import com.example.Aisian.Cuisine.dto.CartResponseDTO;
import com.example.Aisian.Cuisine.dto.CartUpdateRequestDTO;
import com.example.Aisian.Cuisine.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequestDTO request, Authentication authentication) {
        try {
            // ✅ Lấy userId từ authentication (JWT đã xác thực)
            Long userId = Long.parseLong(authentication.getName());

            cartService.addToCart(userId, request);
            return ResponseEntity.ok("Đã thêm vào giỏ hàng thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody CartUpdateRequestDTO request, Authentication authentication) {
        try {
            Long userId = Long.parseLong(authentication.getName());
            cartService.updateCart(userId, request);
            return ResponseEntity.ok("Cập nhật giỏ hàng thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeItem(@PathVariable Long productId, Authentication authentication) {
        try {
            Long userId = Long.parseLong(authentication.getName());
            cartService.removeItem(userId, productId);
            return ResponseEntity.ok("Đã xóa sản phẩm khỏi giỏ hàng!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {
        try {
            Long userId = Long.parseLong(authentication.getName());
            CartResponseDTO response = cartService.getCart(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

}
