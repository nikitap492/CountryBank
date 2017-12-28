package com.cbank.domain.credit;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

import static com.cbank.domain.credit.CreditDirection.CREDIT;
import static com.cbank.domain.credit.CreditDirection.DEPOSIT;

@ToString
@Getter
@RequiredArgsConstructor
public enum CreditType {
    PERSONAL_CREDIT(9, CREDIT), BUSINESS_CREDIT(15, CREDIT), BUSINESS_DEPOSIT(4, DEPOSIT);

    public static final Set<CreditType> CREDITS = Sets.immutableEnumSet(PERSONAL_CREDIT, BUSINESS_CREDIT);

    private final int rate;
    private final CreditDirection direction;
    private BigDecimal percent;

    public BigDecimal getPercent(){
        if (percent == null){
            percent = new BigDecimal(rate ).divide( new BigDecimal(100), 2, BigDecimal.ROUND_CEILING);
        }
        return percent;
    }
}
