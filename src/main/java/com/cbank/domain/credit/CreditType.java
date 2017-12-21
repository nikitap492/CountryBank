package com.cbank.domain.credit;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

import static com.cbank.domain.credit.CreditDirection.CREDIT;
import static com.cbank.domain.credit.CreditDirection.DEPOSIT;

@ToString
@Getter
@AllArgsConstructor
public enum CreditType {
    PERSONAL_CREDIT(9, CREDIT), BUSINESS_CREDIT(15, CREDIT), BUSINESS_DEPOSIT(3, DEPOSIT);

    public static final Set<CreditType> CREDITS = Sets.immutableEnumSet(PERSONAL_CREDIT, BUSINESS_CREDIT);

    private final int rate;
    private final CreditDirection direction;

    public BigDecimal getPercent(){
        return new BigDecimal(rate / 100);
    }
}
