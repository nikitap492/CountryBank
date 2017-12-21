package com.cbank.services;

import com.cbank.domain.Account;
import com.cbank.domain.credit.Credit;
import com.cbank.domain.transaction.Transaction;
import com.cbank.domain.transaction.TransactionAccountProjection;

import java.util.Collection;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface TransactionService {

    Transaction create(Transaction transaction);

    Collection<TransactionAccountProjection> byAccount(String accountNum);

    Transaction creditWithdraw(Account account, Credit credit);

    Transaction credit(Account account, Credit credit);
}
