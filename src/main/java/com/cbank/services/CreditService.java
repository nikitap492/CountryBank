package com.cbank.services;

import com.cbank.domain.credit.Credit;

/**
 * @author Podshivalov N.A.
 * @since 20.12.2017.
 */
public interface CreditService{

    Credit create(Credit credit);

    void withdraw(Credit credit);


}
