package com.bank.domain.services.credit;


import lombok.Getter;

@Getter
public enum CreditFrequency {
    MONTH("Monthly"), WEAK("Weakly");

    private final String freq;

    CreditFrequency(String s) {
        this.freq = s;
    }

}