package com.cbank.services.impl.credit;

import com.cbank.domain.credit.Credit;
import com.cbank.domain.credit.CreditState;
import com.cbank.repositories.AccountRepository;
import com.cbank.repositories.CreditRepository;
import com.cbank.services.CreditService;
import com.cbank.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Podshivalov N.A.
 * @since 20.12.2017.
 */
@Service
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Credit create(Credit credit){
        val account = accountRepository.findOne(credit.getAccountId());
        transactionService.credit(account, credit);
        return creditRepository.save(credit);
    }

    @Override
    @Transactional
    public void withdraw(Credit credit){
        val account = accountRepository.findOne(credit.getAccountId());
        transactionService.creditWithdraw(account, credit);

        if(LocalDateTime.now().isAfter(credit.getClosedAt())){
            credit.setState(CreditState.CLOSED);
            creditRepository.save(credit);
        }
    }

}
