package com.randyshreeves.home_affordability_calculator.controller;

import com.randyshreeves.home_affordability_calculator.dto.AffordabilityRequest;
import com.randyshreeves.home_affordability_calculator.dto.AffordabilityResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/affordability")
public class AffordabilityController {

    @PostMapping("/calculate")
    public AffordabilityResponse calculateAffordability(@RequestBody AffordabilityRequest request) {
        System.out.println("Monthly Payment: " + request.getMonthlyPayment());
        System.out.println("Interest Rate: " + request.getInterestRate());
        return new AffordabilityResponse(425000);
    }
}
