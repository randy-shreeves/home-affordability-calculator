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
 * Rearranged formula:
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
        double hoaMonthlyFees = request.getHoaMonthlyFees();
        double propertyTaxRate = request.getPropertyTaxRate() / 100 / 12;
        double homeownersInsuranceRate = request.getHomeownersInsuranceRate() / 100 / 12;
        double maxHomePrice = calculateMaxHomePrice(
                monthlyPayment,
                downPayment,
                monthlyInterestRate,
                loanTermMonths,
                hoaMonthlyFees,
                propertyTaxRate,
                homeownersInsuranceRate);
        return new HomeAffordabilityResponse(maxHomePrice);
    }

    private double calculateMaxHomePrice(
            double monthlyPayment,
            double downPayment,
            double monthlyInterestRate,
            int loanTermMonths,
            double hoaMonthlyFees,
            double propertyTaxRate,
            double homeownersInsuranceRate) {
        if (monthlyPayment < 0 || loanTermMonths <= 0) { // handle invalid mortgage configurations
            return 0;
        }
        monthlyPayment -= hoaMonthlyFees;
        if (monthlyPayment <= 0) { // handle HOA being greater than desired monthly payment
            return 0;
        }
        double numerator = calculateNumerator(monthlyInterestRate, loanTermMonths);
        double denominator = calculateDenominator(monthlyInterestRate, loanTermMonths);
        double maxHomePrice;
        if (numerator == 0) { // handle edge case if user enters 0% interest rate
            maxHomePrice = monthlyPayment * loanTermMonths;
        } else {
            maxHomePrice = (monthlyPayment * denominator) / numerator;
        }
        maxHomePrice = Math.round(maxHomePrice + downPayment);
        double adjustedHomePrice = adjustForTaxesAndInsurance(
                monthlyPayment,
                maxHomePrice,
                downPayment,
                propertyTaxRate,
                homeownersInsuranceRate,
                monthlyInterestRate,
                loanTermMonths
        );
        return Math.round(adjustedHomePrice);
    }

    // Numerator of mortgage amortization formula: r(1+r)^n
    private double calculateNumerator(double monthlyInterestRate, int loanTermMonths) {
        return monthlyInterestRate * Math.pow((1 + monthlyInterestRate), loanTermMonths);
    }

    // Denominator of mortgage amortization formula: (1+r)^n - 1
    private double calculateDenominator(double monthlyInterestRate, int loanTermMonths) {
        return Math.pow((1 + monthlyInterestRate), loanTermMonths) - 1;
    }

    private double calculateMonthlyMortgagePayment(
            double homePrice,
            double downPayment,
            double monthlyInterestRate,
            int loanTermMonths) {
        double monthlyPayment;
        double loanAmount = homePrice - downPayment;
        if (monthlyInterestRate == 0) {
            monthlyPayment = loanAmount / loanTermMonths;
        } else {
            double numerator = calculateNumerator(monthlyInterestRate, loanTermMonths);
            double denominator = calculateDenominator(monthlyInterestRate, loanTermMonths);
            monthlyPayment = loanAmount * numerator / denominator;
        }
        return monthlyPayment;
    }

    private double adjustForTaxesAndInsurance(
            double monthlyPayment,
            double maxHomePrice,
            double downPayment,
            double propertyTaxRate,
            double homeownersInsuranceRate,
            double monthlyInterestRate,
            int loanTermMonths) {
        while (true) {
            double monthlyTaxPayment = maxHomePrice * propertyTaxRate;
            double monthlyInsurancePayment = maxHomePrice * homeownersInsuranceRate;
            double mortgagePayment = calculateMonthlyMortgagePayment(maxHomePrice, downPayment, monthlyInterestRate, loanTermMonths);
            double totalMonthlyCost = mortgagePayment + monthlyTaxPayment + monthlyInsurancePayment;
            if (totalMonthlyCost <= monthlyPayment) {
                return maxHomePrice;
            }
            maxHomePrice--;
        }
    }
}
