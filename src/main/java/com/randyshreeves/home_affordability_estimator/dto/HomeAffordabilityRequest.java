package com.randyshreeves.home_affordability_estimator.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class HomeAffordabilityRequest {

    @PositiveOrZero(message = "Monthly payment must be zero or greater")
    private double monthlyPayment;

    @PositiveOrZero(message = "Down payment must be zero or greater")
    private double downPayment;

    @PositiveOrZero(message = "Interest rate must be zero or greater")
    private double interestRate;

    @Positive(message = "Loan term must be greater than zero")
    private int loanTermYears;

    @PositiveOrZero(message = "Property tax rate must be zero or greater.")
    private double propertyTaxRate;

    @PositiveOrZero(message = "Homeowner's insurance must be zero or greater.")
    private double homeownersInsurance;

    @Positive(message = "PMI rate must be zero or greater.")
    private double pmiRate;

    @Positive(message = "HOA monthly fees must be zero or greater.")
    private double hoaMonthlyFees;

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanTermYears() {
        return loanTermYears;
    }

    public void setLoanTermYears(int loanTermYears) {
        this.loanTermYears = loanTermYears;
    }

    public double getPropertyTaxRate() {
        return propertyTaxRate;
    }

    public void setPropertyTaxRate(double propertyTaxRate) {
        this.propertyTaxRate = propertyTaxRate;
    }

    public double getHomeownersInsurance() {
        return homeownersInsurance;
    }

    public void setHomeownersInsurance(double homeownersInsurance) {
        this.homeownersInsurance = homeownersInsurance;
    }

    public double getPmiRate() {
        return pmiRate;
    }

    public void setPmiRate(double pmiRate) {
        this.pmiRate = pmiRate;
    }

    public double getHoaMonthlyFees() {
        return hoaMonthlyFees;
    }

    public void setHoaMonthlyFees(double hoaMonthlyFees) {
        this.hoaMonthlyFees = hoaMonthlyFees;
    }
}
