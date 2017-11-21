package com.cbank.services;

import com.cbank.domain.transaction.Transaction;

import java.math.BigDecimal;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface  TariffService {

    BigDecimal evaluate(Transaction transaction);

}
