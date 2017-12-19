package com.cbank.services.impl;

import com.cbank.domain.transaction.Transaction;
import com.cbank.exceptions.InsufficientFundsException;
import com.cbank.repositories.TransactionRepository;
import com.cbank.services.AccountService;
import com.cbank.services.BalanceService;
import com.cbank.services.TariffService;
import com.cbank.services.TransactionService;
import com.cbank.validators.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private BalanceService balanceService;
    private TariffService tariffService;
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction create(Transaction transaction) {
        val recipient = transaction.getRecipient();
        val payer = transaction.getPayer();
        val amount = transaction.getAmount();

        ValidationUtils.zeroAmount(amount);
        ValidationUtils.account(payer);
        ValidationUtils.account(recipient);

        val balance = balanceService.balance(payer);
        val byTariff = tariffService.evaluate(transaction);

        if (amount.add(byTariff).compareTo(balance) > 0)
            throw new InsufficientFundsException();

        transactionRepository.save(transaction);
        if (byTariff.compareTo(BigDecimal.ZERO) > 0){
            val commission = Transaction.builder()
                    .payer(payer)
                    .recipient(AccountService.BANK_ACCOUNT)
                    .createdAt(LocalDateTime.now())
                    .amount(byTariff)
                    .details("Commission for transaction " + transaction.getId())
                    .build();

            transactionRepository.save(commission);
        }

        return transaction;
    }

    @Override
    public Collection<Transaction> byAccount(String accountNum) {
        return transactionRepository.findAllByAccountNum(accountNum);
    }
}
