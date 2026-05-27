package com.randyshreeves.home_affordability_estimator.controller;

import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityRequest;
import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityResponse;
import com.randyshreeves.home_affordability_estimator.service.HomeAffordabilityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/affordability")
public class HomeAffordabilityController {

    private final HomeAffordabilityService homeAffordabilityService;

    public HomeAffordabilityController(HomeAffordabilityService homeAffordabilityService) {
        this.homeAffordabilityService = homeAffordabilityService;
    }

    @PostMapping("/estimate")
    public HomeAffordabilityResponse calculateAffordability(@RequestBody HomeAffordabilityRequest request) {
        return homeAffordabilityService.calculateAffordability(request);
    }
}
