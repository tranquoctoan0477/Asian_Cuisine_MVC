package com.example.Aisian.Cuisine.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "123456"; // mật khẩu bạn muốn mã hóa
        String hash = encoder.encode(raw);

        System.out.println("Hash: " + hash);
        System.out.println("Match? " + encoder.matches("123456", hash));
    }
}
