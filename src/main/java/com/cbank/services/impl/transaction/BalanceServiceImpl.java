package com.cbank.services.impl.transaction;

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

    @Override
    public BigDecimal balance(String accountNum) {
        return transactionRepository.findAllByAccountNum(accountNum).stream()
                .map(tr -> tr.getPayer().equals(accountNum) ? tr.getAmount() : tr.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
