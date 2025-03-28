package com.example.Aisian.Cuisine.controller;

import com.example.Aisian.Cuisine.dto.HomeResponse;
import com.example.Aisian.Cuisine.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private HomeService homeService;

    // Cập nhật API Home để nhận page và limit
    @GetMapping("/home")
    public HomeResponse getHomeData(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit) {

        return homeService.getHomeData(search, page, limit);
    }
}
