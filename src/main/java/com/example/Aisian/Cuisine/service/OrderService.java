package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.dto.OrderDTO;
import com.example.Aisian.Cuisine.dto.OrderItemDTO;
import com.example.Aisian.Cuisine.model.Order;
import com.example.Aisian.Cuisine.model.OrderItem;
import com.example.Aisian.Cuisine.repository.OrderRepository;
import com.example.Aisian.Cuisine.repository.OrderItemRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // 📌 Lấy danh sách đơn hàng của người dùng hiện tại
    public List<OrderDTO> getOrdersForCurrentUser() {
        Long userId = getCurrentUserId();

        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getStatus(),
                        order.getTotal(),
                        convertTimestampToLocalDate(order.getCreatedAt()) // ✅ Fix lỗi chuyển đổi
                ))
                .collect(Collectors.toList());
    }

    // 📌 Lấy danh sách sản phẩm trong đơn hàng (phải kiểm tra xem đơn hàng có thuộc về user không)
    public List<OrderItemDTO> getOrderItems(Long orderId) {
        Long userId = getCurrentUserId();

        // Kiểm tra đơn hàng có tồn tại và thuộc về user hiện tại không
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty() || !orderOpt.get().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Không tìm thấy đơn hàng hoặc đơn hàng không thuộc về bạn!");
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream().map(item -> new OrderItemDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getNote(),
                item.getProduct().getMainImg()
        )).collect(Collectors.toList());
    }

    // 📌 Lấy userId từ JWT một cách an toàn hơn
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Người dùng chưa đăng nhập!");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return Long.parseLong(((UserDetails) principal).getUsername()); // JWT đang lưu userId dưới dạng username
        } else {
            return Long.parseLong(principal.toString());
        }
    }

    // 📌 Chuyển Timestamp -> LocalDate
    private LocalDate convertTimestampToLocalDate(Timestamp timestamp) {
        return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
