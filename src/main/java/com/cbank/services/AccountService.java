package com.cbank.services;

import com.cbank.domain.Account;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface AccountService extends PersistableService<Account> {
    String GOVERNMENT_ACCOUNT = "9999999999999999";
    String BANK_ACCOUNT = "0000000000000000";
    BigDecimal NEW_ACCOUNT_PRICE = BigDecimal.valueOf(30);

    Optional<Account> current(Long clientId);

    Account asCurrent(Account current, String accountNum);

    Account create(Account current);

    Collection<Account> byClient(Long clientId);
}
