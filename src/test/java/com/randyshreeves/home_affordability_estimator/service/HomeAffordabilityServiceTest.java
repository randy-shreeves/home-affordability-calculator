package com.randyshreeves.home_affordability_estimator.service;

import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityRequest;
import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HomeAffordabilityServiceTest {

    private final HomeAffordabilityService service = new HomeAffordabilityService();

    @Test
    void testBasicAffordabilityCalculation() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(50000);
        request.setInterestRate(6.5);
        request.setLoanTermYears(30);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(445527.0, response.getMaxHomePrice());
    }

    @Test
    void testZeroInterestLoan() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(50000);
        request.setInterestRate(0);
        request.setLoanTermYears(30);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        double expected = 2500 * 360 + 50000;
        assertEquals(expected, response.getMaxHomePrice());
    }

    @Test
    void testNegativeMonthlyPayment() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(-2500);
        request.setDownPayment(50000);
        request.setInterestRate(6.5);
        request.setLoanTermYears(30);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(0, response.getMaxHomePrice());
    }

    @Test
    void testNegativeLoanTerm() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(50000);
        request.setInterestRate(6.5);
        request.setLoanTermYears(-30);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(0, response.getMaxHomePrice());
    }
}
