package com.cbank.services.impl.tariff;

import com.cbank.domain.transaction.Transaction;
import com.cbank.services.AccountService;
import com.cbank.services.TariffService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Service
@AllArgsConstructor
public class TariffResolver implements TariffService{

    @Override
    public BigDecimal evaluate(Transaction transaction) {
        if (AccountService.BANK_ACCOUNT.equals(transaction.getRecipient())){
            return TariffHolder.NONE.evaluate(transaction);
        }

        val tariff = AccountService.GOVERNMENT_ACCOUNT.equals(transaction.getRecipient())
                ? TariffHolder.BUDGET_TRANSACTION
                : AccountService.BANK_ACCOUNT.equals(transaction.getRecipient())
                    ? TariffHolder.NONE
                    : TariffHolder.GENERAL_TRANSACTION;

        return tariff.evaluate(transaction);
    }
}
