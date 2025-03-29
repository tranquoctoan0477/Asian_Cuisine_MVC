package com.example.Aisian.Cuisine.controller;

import com.example.Aisian.Cuisine.dto.CartRequestDTO;
import com.example.Aisian.Cuisine.dto.CartResponseDTO;
import com.example.Aisian.Cuisine.dto.CartUpdateRequestDTO;
import com.example.Aisian.Cuisine.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

            // ✅ Trả về JSON hợp lệ
            Map<String, String> response = new HashMap<>();
            response.put("message", "Đã thêm vào giỏ hàng thành công!");

            return ResponseEntity.ok(response); // Trả về JSON thay vì String
        } catch (Exception e) {
            // ✅ Trả về lỗi dưới dạng JSON
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Lỗi: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody CartUpdateRequestDTO request, Authentication authentication) {
        try {
            Long userId = Long.parseLong(authentication.getName());
            cartService.updateCart(userId, request);

            // ✅ Trả về JSON hợp lệ
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cập nhật giỏ hàng thành công!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeItem(@PathVariable Long productId, Authentication authentication) {
        try {
            Long userId = Long.parseLong(authentication.getName());
            cartService.removeItem(userId, productId);

            // ✅ Trả về JSON hợp lệ
            Map<String, String> response = new HashMap<>();
            response.put("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {
        try {
            Long userId  = Long.parseLong(authentication.getName());
            CartResponseDTO response = cartService.getCart(userId);

            // ✅ Trả về JSON hợp lệ
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Lỗi: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
