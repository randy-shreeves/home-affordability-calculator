package com.randyshreeves.home_affordability_estimator.dto;

public class HomeAffordabilityRequest {

    private double monthlyPayment;
    private double downPayment;
    private double interestRate;
    private int loanTermYears;
    private double propertyTaxRate;
    private double homeownersInsurance;
    private double pmiRate;
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
