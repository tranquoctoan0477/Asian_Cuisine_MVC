package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.dto.CartItemResponseDTO;
import com.example.Aisian.Cuisine.dto.CartRequestDTO;
import com.example.Aisian.Cuisine.dto.CartResponseDTO;
import com.example.Aisian.Cuisine.dto.CartUpdateRequestDTO;
import com.example.Aisian.Cuisine.model.Order;
import com.example.Aisian.Cuisine.model.OrderItem;
import com.example.Aisian.Cuisine.model.Product;
import com.example.Aisian.Cuisine.repository.OrderItemRepository;
import com.example.Aisian.Cuisine.repository.OrderRepository;
import com.example.Aisian.Cuisine.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addToCart(Long userId, CartRequestDTO request) {
        // 1. Tìm đơn hàng 'pending' của user
        Optional<Order> optionalOrder = orderRepository.findByUserIdAndStatus(userId, "pending");
        Order order;

        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            // Nếu chưa có, tạo đơn mới
            order = new Order();
            order.setUserId(userId);
            order.setStatus("pending");
            order.setTotal(BigDecimal.ZERO); // Ban đầu total là 0
            orderRepository.save(order);
        }

        // 2. Lấy thông tin sản phẩm
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 3. Kiểm tra xem sản phẩm đã có trong giỏ chưa
        Optional<OrderItem> optionalItem = orderItemRepository.findByOrderIdAndProductId(order.getId(), request.getProductId());

        if (optionalItem.isPresent()) {
            // Nếu đã có thì tăng số lượng
            OrderItem item = optionalItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            orderItemRepository.save(item);
        } else {
            // Nếu chưa có thì thêm mới
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getPrice());
            orderItemRepository.save(item);
        }

        // 4. Cập nhật lại total
        BigDecimal currentTotal = orderItemRepository.calculateTotalByOrderId(order.getId());
        order.setTotal(currentTotal);
        orderRepository.save(order);
    }


    @Override
    public void updateCart(Long userId, CartUpdateRequestDTO request) {
        // 1. Tìm đơn hàng 'pending' của user
        Order order = orderRepository.findByUserIdAndStatus(userId, "pending")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        // 2. Tìm item trong giỏ
        OrderItem item = orderItemRepository.findByOrderIdAndProductId(order.getId(), request.getProductId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ"));

        // 3. Nếu quantity = 0 thì xóa item
        if (request.getQuantity() <= 0) {
            orderItemRepository.delete(item);
        } else {
            item.setQuantity(request.getQuantity());

            // ✅ Cập nhật thêm note nếu có
            if (request.getNote() != null && !request.getNote().isEmpty()) {
                item.setNote(request.getNote());
            }

            orderItemRepository.save(item);
        }

        // 4. Cập nhật lại total
        BigDecimal newTotal = orderItemRepository.calculateTotalByOrderId(order.getId());
        order.setTotal(newTotal != null ? newTotal : BigDecimal.ZERO);
        orderRepository.save(order);
    }

    @Override
    public void removeItem(Long userId, Long productId) {
        // 1. Tìm đơn hàng 'pending' của user
        Order order = orderRepository.findByUserIdAndStatus(userId, "pending")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        // 2. Tìm item trong giỏ
        OrderItem item = orderItemRepository.findByOrderIdAndProductId(order.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ"));

        // 3. Xóa item
        orderItemRepository.delete(item);

        // 4. Cập nhật lại total
        BigDecimal newTotal = orderItemRepository.calculateTotalByOrderId(order.getId());
        order.setTotal(newTotal != null ? newTotal : BigDecimal.ZERO);
        orderRepository.save(order);
    }

    @Override
    public CartResponseDTO getCart(Long userId) {
        Order order = orderRepository.findByUserIdAndStatus(userId, "pending")
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        List<CartItemResponseDTO> itemDTOs = items.stream().map(item -> {
            Product product = item.getProduct();
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            return new CartItemResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getMainImg(),
                    product.getPrice(),
                    item.getQuantity(),
                    subtotal
            );
        }).toList();

        return new CartResponseDTO(itemDTOs, order.getTotal());
    }

}
