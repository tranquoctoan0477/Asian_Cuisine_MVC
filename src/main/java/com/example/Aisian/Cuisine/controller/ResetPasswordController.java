package com.example.Aisian.Cuisine.controller;

import com.example.Aisian.Cuisine.dto.ResetNewPasswordRequest;
import com.example.Aisian.Cuisine.dto.ResetSendOtpRequest;
import com.example.Aisian.Cuisine.dto.ResetVerifyOtpRequest;
import com.example.Aisian.Cuisine.service.ResetPasswordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reset")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody ResetSendOtpRequest request) {
        boolean success = resetPasswordService.sendOtpToPhone(request.getPhoneNumber());
        if (success) {
            return ResponseEntity.ok("Đã gửi mã OTP về số điện thoại");
        } else {
            return ResponseEntity.badRequest().body("Không thể gửi OTP. Vui lòng thử lại sau.");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody ResetVerifyOtpRequest request) {
        boolean isValid = resetPasswordService.verifyOtp(request.getPhoneNumber(), request.getOtp());

        if (isValid) {
            return ResponseEntity.ok("✅ Mã OTP hợp lệ. Bạn có thể đặt mật khẩu mới.");
        } else {
            return ResponseEntity.badRequest().body("❌ Mã OTP không đúng hoặc đã hết hạn.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetNewPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("❌ Mật khẩu không khớp.");
        }

        boolean result = resetPasswordService.updatePassword(request.getPhoneNumber(), request.getNewPassword());

        if (result) {
            return ResponseEntity.ok("✅ Đặt lại mật khẩu thành công.");
        } else {
            return ResponseEntity.badRequest().body("❌ Không tìm thấy người dùng.");
        }
    }


}
