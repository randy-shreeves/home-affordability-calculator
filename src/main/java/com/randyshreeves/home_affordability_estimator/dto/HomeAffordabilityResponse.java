package com.randyshreeves.home_affordability_estimator.dto;

public class HomeAffordabilityResponse {

    private long maxHomePrice;

    public HomeAffordabilityResponse(long maxHomePrice) {
        this.maxHomePrice = maxHomePrice;
    }

    public long getMaxHomePrice() {
        return maxHomePrice;
    }
}
