package com.randyshreeves.home_affordability_estimator.service;

import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityRequest;
import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityResponse;
import org.springframework.stereotype.Service;

@Service
public class HomeAffordabilityService {

    public HomeAffordabilityResponse calculateAffordability(HomeAffordabilityRequest request) {
        double monthlyPayment = request.getMonthlyPayment();
        double downPayment = request.getDownPayment();
        double interestRate = request.getInterestRate() / 100 / 12;
        int loanTermMonths = request.getLoanTermYears() * 12;
        double maxHomePrice;
        double numerator = calculateNumerator(interestRate, loanTermMonths);
        double denominator = calculateDenominator(interestRate, loanTermMonths);

        if (monthlyPayment < 0) {
            maxHomePrice = 0;
        } else if (numerator == 0) {
            maxHomePrice = monthlyPayment * loanTermMonths + downPayment;
        } else {
            maxHomePrice = ((monthlyPayment * denominator) / numerator) + downPayment;
        }

        maxHomePrice = Math.round(maxHomePrice);
        return new HomeAffordabilityResponse(maxHomePrice);
    }

    private double calculateNumerator(double interestRate, int loanTermMonths) {
        return interestRate * Math.pow((1 + interestRate), loanTermMonths);
    }

    private double calculateDenominator(double interestRate, int loanTermMonths) {
        return Math.pow((1 + interestRate), loanTermMonths) - 1;
    }
}
