package com.randyshreeves.home_affordability_calculator.controller;

import com.randyshreeves.home_affordability_calculator.dto.HomeAffordabilityRequest;
import com.randyshreeves.home_affordability_calculator.dto.HomeAffordabilityResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/affordability")
public class HomeAffordabilityController {

    @PostMapping("/calculate")
    public HomeAffordabilityResponse calculateAffordability(@RequestBody HomeAffordabilityRequest request) {
        System.out.println("Monthly Payment: " + request.getMonthlyPayment());
        System.out.println("Interest Rate: " + request.getInterestRate());
        return new HomeAffordabilityResponse(425000);
    }
}
