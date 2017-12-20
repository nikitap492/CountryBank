package com.cbank.domain.credit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

import static com.cbank.domain.credit.CreditDirection.CREDIT;
import static com.cbank.domain.credit.CreditDirection.DEPOSIT;

@ToString
@Getter
@AllArgsConstructor
public enum CreditType {
    PERSONAL_CREDIT(9, CREDIT), BUSINESS_CREDIT(15, CREDIT), BUSINESS_DEPOSIT(3, DEPOSIT);

    private final int rate;
    private final CreditDirection direction;

    public BigDecimal getPercent(){
        return new BigDecimal(rate / 100);
    }
}
