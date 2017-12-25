package com.cbank.services.impl.credit;

import com.cbank.domain.Account;
import com.cbank.domain.credit.Credit;
import com.cbank.domain.credit.CreditState;
import com.cbank.domain.credit.CreditType;
import lombok.val;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ROUND_CEILING;

/**
 * @author Podshivalov N.A.
 * @since 20.12.2017.
 */
@Component
public class CreditFactory {

    public Credit create(Account account, CreditType type, BigDecimal initialAmount, Integer numOfMonths){
        val credit = new Credit();
        credit.setState(CreditState.OPENED);
        credit.setInitialAmount(initialAmount);
        credit.setAccountId(account.getId());
        credit.setNumOfWithdraws(numOfMonths);
        credit.setType(type);
        credit.setMonthlySum(calculateMonthlyWithdrawAmount(type, initialAmount, numOfMonths));
        credit.setClosedAt(LocalDateTime.now().plusMonths(numOfMonths));
        return credit;
    }

    private BigDecimal calculateMonthlyWithdrawAmount(CreditType type, BigDecimal initialAmount, Integer numOfMonths){
        val rate = type.getPercent();
        val months = new BigDecimal(numOfMonths);
        val initialPerMonth = initialAmount.divide(months, 2, ROUND_CEILING);
        return initialPerMonth.multiply(rate);
    }






}
