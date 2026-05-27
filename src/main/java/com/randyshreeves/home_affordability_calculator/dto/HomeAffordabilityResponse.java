package com.randyshreeves.home_affordability_calculator.dto;

public class HomeAffordabilityResponse {

    private double maxHomePrice;

    public HomeAffordabilityResponse(double maxHomePrice) {
        this.maxHomePrice = maxHomePrice;
    }

    public double getMaxHomePrice() {
        return maxHomePrice;
    }
}
