package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.dto.LoginResponse;
import com.example.Aisian.Cuisine.dto.RegisterRequest;
import com.example.Aisian.Cuisine.model.User;
import com.example.Aisian.Cuisine.repository.UserRepository;
import com.example.Aisian.Cuisine.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email not found");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ Gọi version mới của generateToken(userId, email)
        String accessToken = jwtUtil.generateToken(user.getId(), user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public LoginResponse refreshAccessToken(String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtUtil.getEmailFromToken(refreshToken);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("Refresh token mismatch");
        }

        // ✅ Gọi version mới của generateToken(userId, email)
        String newAccessToken = jwtUtil.generateToken(user.getId(), user.getEmail());

        return new LoginResponse(
                newAccessToken,
                refreshToken,
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }


    public void register(RegisterRequest request) {
        // Kiểm tra email rỗng và định dạng
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new RuntimeException("Email không được để trống");
        }
        if (!request.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            throw new RuntimeException("Email không đúng định dạng");
        }

        // Kiểm tra mật khẩu
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new RuntimeException("Mật khẩu phải từ 6 ký tự trở lên");
        }

        // Kiểm tra username
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new RuntimeException("Tên đăng nhập không được để trống");
        }

        // Kiểm tra số điện thoại
        if (request.getPhone() == null || !request.getPhone().matches("^\\d{9,11}$")) {
            throw new RuntimeException("Số điện thoại không hợp lệ");
        }

        // Kiểm tra giới tính (Nam hoặc Nữ)
        if (request.getGender() == null ||
                (!request.getGender().equalsIgnoreCase("Nam") && !request.getGender().equalsIgnoreCase("Nữ"))) {
            throw new RuntimeException("Giới tính chỉ được là Nam hoặc Nữ");
        }

        // Kiểm tra email trùng
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // Tạo user mới
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setPhone(request.getPhone());
        newUser.setGender(request.getGender());
        newUser.setRole("user");

        userRepository.save(newUser);
    }




}
