package com.bank.validators;

import com.bank.domain.Bill;

/**
 * Special entity for validation of bill and money at the same time
 * Needs for transfer creation
 */

public class MovementValidationAnswer {
    private Bill bill;
    private Double money;

    MovementValidationAnswer(Bill bill, Double money) {
        this.bill = bill;
        this.money = money;
    }

    public Bill getBill() {
        return bill;
    }

    public Double getMoney() {
        return money;
    }
}