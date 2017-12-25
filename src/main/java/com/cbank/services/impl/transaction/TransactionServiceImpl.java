package com.cbank.services.impl.transaction;

import com.cbank.domain.Account;
import com.cbank.domain.credit.Credit;
import com.cbank.domain.credit.CreditDirection;
import com.cbank.domain.transaction.Transaction;
import com.cbank.domain.transaction.TransactionAccountProjection;
import com.cbank.exceptions.InsufficientFundsException;
import com.cbank.repositories.TransactionRepository;
import com.cbank.services.BalanceService;
import com.cbank.services.TariffService;
import com.cbank.services.TransactionService;
import com.cbank.validators.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

import static com.cbank.services.AccountService.BANK_ACCOUNT;
import static java.util.stream.Collectors.toList;

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
        if (byTariff.compareTo(BigDecimal.ZERO) > 0) {
            val commission = Transaction.builder()
                    .payer(payer)
                    .recipient(BANK_ACCOUNT)
                    .createdAt(LocalDateTime.now())
                    .amount(byTariff)
                    .details("Commission for transaction " + transaction.getId())
                    .build();

            transactionRepository.save(commission);
        }

        return transaction;
    }

    @Override
    @Transactional
    public Transaction creditWithdraw(Account account, Credit credit) {
        val direction = credit.getType().getDirection();
        val sides = resolveSides(direction, account.getNum(), true);
        val transaction = Transaction.builder()
                .payer(sides.getFirst())
                .recipient(sides.getSecond())
                .amount(credit.getMonthlySum())
                .details(String.format("The %s's monthly payment", credit.getTypeString()))
                .build();

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction credit(Account account, Credit credit) {
        val direction = credit.getType().getDirection();
        val sides = resolveSides(direction, account.getNum(), false);
        val amount = credit.getInitialAmount();

        if (direction == CreditDirection.DEPOSIT && balanceService.balance(sides.getFirst()).compareTo(amount) < 0){
            throw new InsufficientFundsException();
        }

        val transaction = Transaction.builder()
                .payer(sides.getFirst())
                .recipient(sides.getSecond())
                .amount(credit.getInitialAmount())
                .details(String.format("The %s was approved", credit.getTypeString()))
                .build();

        return transactionRepository.save(transaction);
    }

    private Pair<String, String> resolveSides(CreditDirection direction, String accountNum, boolean withdraw){
        return direction == CreditDirection.CREDIT ^ withdraw
                ? Pair.of(BANK_ACCOUNT, accountNum)
                : Pair.of(accountNum, BANK_ACCOUNT);
    }


    @Override
    public Collection<TransactionAccountProjection> byAccount(String accountNum) {
        return transactionRepository.findAllByAccountNum(accountNum).stream()
                .map(tr -> TransactionAccountProjection.from(tr, accountNum))
                .collect(toList());
    }
}
