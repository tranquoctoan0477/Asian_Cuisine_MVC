package com.example.Aisian.Cuisine.dto;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String email;
    private String role;

    public LoginResponse(String accessToken, String refreshToken, String username, String email, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
