package com.bank.domain.services.credit;


public enum CreditFrequency {
    MONTH("Monthly"), WEAK("Weakly");

    private final String freq;

    CreditFrequency(String s) {
        this.freq = s;
    }

    public String getFreq() {
        return freq;
    }
}