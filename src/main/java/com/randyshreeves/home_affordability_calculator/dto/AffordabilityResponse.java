package com.randyshreeves.home_affordability_calculator.dto;

public class AffordabilityResponse {

    private double maxHomePrice;

    public AffordabilityResponse(double maxHomePrice) {
        this.maxHomePrice = maxHomePrice;
    }

    public double getMaxHomePrice() {
        return maxHomePrice;
    }
}
