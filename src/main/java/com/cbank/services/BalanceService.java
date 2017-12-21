package com.cbank.services;

import com.cbank.domain.Account;

import java.math.BigDecimal;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface BalanceService {

    default BigDecimal balance(Account account){
        return balance(account.getNum());
    }

    BigDecimal balance(String accountNum);

    BigDecimal balance(Long accountId);
}
