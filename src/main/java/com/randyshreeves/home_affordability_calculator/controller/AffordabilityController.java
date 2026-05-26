package com.randyshreeves.home_affordability_calculator.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/affordability")
public class AffordabilityController {

    @PostMapping("/calculate")
    public Map<String, Object> calculateAffordability(@RequestBody Map<String, Object> request) {
        return Map.of("message", "Route is working", "receivedData", request);
    }
}
