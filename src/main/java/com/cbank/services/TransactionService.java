package com.cbank.services;

import com.cbank.domain.transaction.Transaction;

import java.util.Collection;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface TransactionService {

    Transaction create(Transaction transaction);

    Collection<Transaction> byAccount(String accountNum);
}
