package com.randyshreeves.home_affordability_estimator.service;

import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityRequest;
import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Expected affordability values in these tests were verified
 * against multiple online mortgage calculators using equivalent
 * loan inputs and monthly payment targets.
 */

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
        assertEquals(445527, response.getMaxHomePrice(), 1);
    }

    @Test
    void testZeroInterestLoan() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(50000);
        request.setInterestRate(0);
        request.setLoanTermYears(30);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(950000, response.getMaxHomePrice(), 1);
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

    @Test
    void testHoaReducesAffordability() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(50000);
        request.setInterestRate(6.5);
        request.setLoanTermYears(30);
        request.setHoaMonthlyFees(50);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(437616, response.getMaxHomePrice(), 1);
    }

    @Test
    void testHoaGreaterThanMonthlyPayment() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(50000);
        request.setInterestRate(6.5);
        request.setLoanTermYears(30);
        request.setHoaMonthlyFees(3000);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(0, response.getMaxHomePrice());
    }

    @Test
    void testAdjustForAdditionalHousingCosts() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(50000);
        request.setInterestRate(6.5);
        request.setLoanTermYears(30);
        request.setHoaMonthlyFees(50);
        request.setPropertyTaxRate(1.2);
        request.setHomeownersInsuranceRate(0.75);
        request.setPmiRate(0.5);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(333263, response.getMaxHomePrice(), 1);
    }

    @Test
    void testPmiNotAppliedAtTwentyPercentDown() {
        HomeAffordabilityRequest request = new HomeAffordabilityRequest();
        request.setMonthlyPayment(2500);
        request.setDownPayment(100000);
        request.setInterestRate(6.5);
        request.setLoanTermYears(30);
        request.setHoaMonthlyFees(50);
        request.setPropertyTaxRate(1.2);
        request.setHomeownersInsuranceRate(0.75);
        request.setPmiRate(0.5);
        HomeAffordabilityResponse response = service.calculateAffordability(request);
        assertEquals(387892, response.getMaxHomePrice(), 1);
    }
}
