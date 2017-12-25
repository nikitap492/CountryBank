package com.cbank.services.impl.tariff;

import com.cbank.domain.transaction.Transaction;
import com.cbank.services.TariffService;
import lombok.val;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public enum  TariffHolder implements TariffService {
    BUDGET_TRANSACTION {

        @Override
        public BigDecimal evaluate(Transaction transaction) {
            return TEN;
        }



    }, GENERAL_TRANSACTION {
        private final BigDecimal FIFTY = BigDecimal.valueOf(50);
        private final BigDecimal ONE_THOUSAND = BigDecimal.valueOf(1000);
        private final BigDecimal ONE_HUNDRED = new BigDecimal(100);

        private BigDecimal commission(BigDecimal amount, int rate){
            return amount.multiply(BigDecimal.valueOf(rate).divide(ONE_HUNDRED, 2, ROUND_CEILING));
        }

        @Override
        public BigDecimal evaluate(Transaction transaction) {
            val amount = transaction.getAmount();
            return amount.compareTo(FIFTY) < 0
                    ? commission(amount, 3)
                    : amount.compareTo(ONE_THOUSAND) < 0
                        ? commission(amount, 7)
                        : commission(amount, 15);
        }
    },

    NONE {
        @Override
        public BigDecimal evaluate(Transaction transaction) {
            return ZERO;
        }
    }


}
