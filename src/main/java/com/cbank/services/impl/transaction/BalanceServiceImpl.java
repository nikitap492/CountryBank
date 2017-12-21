package com.cbank.services.impl.transaction;

import com.cbank.repositories.AccountRepository;
import com.cbank.repositories.TransactionRepository;
import com.cbank.services.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Podshivalov N.A.
 * @since 22.11.2017.
 */
@Service
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public BigDecimal balance(String accountNum) {
        return transactionRepository.findAllByAccountNum(accountNum).stream()
                .map(tr -> tr.getRecipient().equals(accountNum) ? tr.getAmount() : tr.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal balance(Long accountId) {
        return balance(accountRepository.getOne(accountId));
    }
}
