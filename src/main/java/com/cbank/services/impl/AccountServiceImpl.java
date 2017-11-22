package com.cbank.services.impl;

import com.cbank.domain.Account;
import com.cbank.domain.transaction.Transaction;
import com.cbank.exceptions.InsufficientFundsException;
import com.cbank.repositories.AccountRepository;
import com.cbank.services.AccountService;
import com.cbank.services.BalanceService;
import com.cbank.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final BalanceService balanceService;


    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> current(Long clientId) {
        return accountRepository.findByClientIdAndCurrentIsTrue(clientId);
    }

    @Override
    @Transactional
    public Account asCurrent(Account current, String accountNum) {
        val account = accountRepository.findByNum(accountNum).orElseThrow(EntityNotFoundException::new);
        current.setCurrent(Boolean.FALSE);
        account.setCurrent(Boolean.TRUE);
        save(account); save(current);
        return account;
    }

    @Override
    @Transactional
    public Account create(Account current) {
        val balance = balanceService.balance(current);
        if (NEW_ACCOUNT_PRICE.compareTo( balance) > 0 )
            throw new InsufficientFundsException();

        val transaction = Transaction.builder()
                .payer(current.getNum())
                .amount(NEW_ACCOUNT_PRICE)
                .recipient(BANK_ACCOUNT)
                .details("The payment is for account opening")
                .build();

        transactionService.create(transaction);
        return  accountRepository.save(new Account(current.getClientId()));
    }
}
