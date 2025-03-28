package com.example.Aisian.Cuisine.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/reset/send-otp",         // 👈 Cho phép gửi OTP
                                "/api/reset/verify-otp",       // 👈 Cho phép xác minh OTP
                                "/api/reset/reset-password",   // 👈 Cho phép đặt lại mật khẩu
                                "/static/**",
                                "/images/**",
                                "/css/**", "/js/**"
                        ).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/register", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/login", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/refresh-token", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/home", "GET")).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cart/add").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/cart/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/cart/remove/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/cart").authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 👈 thêm dòng này
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    // CORS như bạn đã có, giữ nguyên
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
