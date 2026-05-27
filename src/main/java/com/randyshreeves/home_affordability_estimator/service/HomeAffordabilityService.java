package com.randyshreeves.home_affordability_estimator.service;

import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityRequest;
import com.randyshreeves.home_affordability_estimator.dto.HomeAffordabilityResponse;
import org.springframework.stereotype.Service;

/*
 * This class implements the standard fixed-rate mortgage amortization formula:
 * M = P * [ r(1 + r)^n ] / [ (1 + r)^n - 1 ]
 * where:
 *   M = monthly payment
 *   P = loan principal
 *   r = monthly interest rate
 *   n = number of payments (months)
 * This implementation rearranges the formula to solve for P (loan principal),
 * allowing the system to estimate the maximum home price based on a target
 * monthly payment and additional cost factors (taxes, insurance, etc.).
 * -------------------------------------------------------------------------
 * Rearranged formula (solving for P):
 * P = M * [ (1 + r)^n - 1 ] / [ r(1 + r)^n ]
 * -------------------------------------------------------------------------
 */

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

        if (monthlyPayment < 0) { // handle edge case if user enters negative desired monthly payment
            maxHomePrice = 0;
        } else if (numerator == 0) { // handle edge case if user enters 0% interest rate
            maxHomePrice = monthlyPayment * loanTermMonths + downPayment;
        } else {
            maxHomePrice = ((monthlyPayment * denominator) / numerator) + downPayment;
        }

        maxHomePrice = Math.round(maxHomePrice);
        return new HomeAffordabilityResponse(maxHomePrice);
    }

    // Numerator of mortgage amortization formula: r(1+r)^n
    private double calculateNumerator(double interestRate, int loanTermMonths) {
        return interestRate * Math.pow((1 + interestRate), loanTermMonths);
    }

    // Denominator of mortgage amortization formula: (1+r)^n - 1
    private double calculateDenominator(double interestRate, int loanTermMonths) {
        return Math.pow((1 + interestRate), loanTermMonths) - 1;
    }
}
