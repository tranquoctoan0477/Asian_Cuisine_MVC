package com.example.Aisian.Cuisine.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cho phép truy cập các ảnh tĩnh trong static folder
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}

