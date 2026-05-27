package com.randyshreeves.home_affordability_estimator.dto;

public class HomeAffordabilityResponse {

    private double maxHomePrice;

    public HomeAffordabilityResponse(double maxHomePrice) {
        this.maxHomePrice = maxHomePrice;
    }

    public double getMaxHomePrice() {
        return maxHomePrice;
    }
}
