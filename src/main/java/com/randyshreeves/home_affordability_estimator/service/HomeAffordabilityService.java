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
        double monthlyInterestRate = request.getInterestRate() / 100 / 12;
        int loanTermMonths = request.getLoanTermYears() * 12;
        double maxHomePrice = calculateMaxHomePrice(monthlyPayment, downPayment, monthlyInterestRate, loanTermMonths);
        return new HomeAffordabilityResponse(maxHomePrice);
    }

    private double calculateMaxHomePrice(double monthlyPayment,
                                         double downPayment,
                                         double monthlyInterestRate,
                                         int loanTermMonths) {
        if (monthlyPayment < 0) { // handle edge case if user enters negative desired monthly payment
            return downPayment;
        }
        double numerator = calculateNumerator(monthlyInterestRate, loanTermMonths);
        double denominator = calculateDenominator(monthlyInterestRate, loanTermMonths);
        double loanAmount;
        if (numerator == 0) { // handle edge case if user enters 0% interest rate
            loanAmount = monthlyPayment * loanTermMonths;
        } else {
            loanAmount = (monthlyPayment * denominator) / numerator;
        }
        return Math.round(loanAmount + downPayment);
    }

    // Numerator of mortgage amortization formula: r(1+r)^n
    private double calculateNumerator(double monthlyInterestRate, int loanTermMonths) {
        return monthlyInterestRate * Math.pow((1 + monthlyInterestRate), loanTermMonths);
    }

    // Denominator of mortgage amortization formula: (1+r)^n - 1
    private double calculateDenominator(double monthlyInterestRate, int loanTermMonths) {
        return Math.pow((1 + monthlyInterestRate), loanTermMonths) - 1;
    }
}
