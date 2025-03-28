package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.model.User;
import com.example.Aisian.Cuisine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Giả lập kho lưu OTP (có thể thay bằng Redis nếu cần)
    private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

    @Autowired
    public ResetPasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean sendOtpToPhone(String phoneNumber) {
        // 👉 Kiểm tra xem số điện thoại có tồn tại không
        Optional<User> optionalUser = userRepository.findByPhone(phoneNumber);
        if (optionalUser.isEmpty()) {
            return false; // Không gửi OTP nếu không có người dùng
        }

        // ✅ Sinh mã OTP 6 chữ số
        String otp = generateOtp();

        // TODO: Gửi OTP bằng SMS thật sự (Twilio, Firebase,...)
        System.out.println("✅ [OTP MOCK] Gửi OTP " + otp + " đến số: " + phoneNumber);

        // Lưu OTP tạm thời
        otpStorage.put(phoneNumber, otp);

        return true;
    }


    // Xác minh OTP
    public boolean verifyOtp(String phoneNumber, String otp) {
        String storedOtp = otpStorage.get(phoneNumber);
        return storedOtp != null && storedOtp.equals(otp);
    }

    // Đổi mật khẩu sau khi xác minh OTP
    public boolean updatePassword(String phoneNumber, String newPassword) {
        Optional<User> optionalUser = userRepository.findByPhone(phoneNumber); // ← CHỈNH ĐÚNG TÊN
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashed = passwordEncoder.encode(newPassword);
            user.setPassword(hashed);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Sinh OTP ngẫu nhiên 6 chữ số
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
