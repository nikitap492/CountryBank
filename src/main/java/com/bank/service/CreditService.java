package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.services.credit.Credit;

/**
 * {@link com.bank.service.impl.CreditServiceImpl}
 */
public interface CreditService {

    void check();

    boolean checkDate(Credit credit);

    void updateStateAndDate(Credit credit);

    void save(Credit credit);

    Credit findById(Long id);

    double sumAllCreditsByAccount(Account account);
}
