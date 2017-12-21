package com.cbank.validators;

import com.cbank.domain.credit.Credit;
import com.cbank.exceptions.InsufficientFundsException;
import com.cbank.exceptions.ValidationException;
import com.cbank.repositories.CreditRepository;
import com.cbank.services.BalanceService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.cbank.domain.credit.CreditType.CREDITS;

@Component
@AllArgsConstructor
public class CreditValidator implements Validator<Credit> {

    /**
     * Constants of personal and business crediting.
     */
    private static final BigDecimal PERSONAL_MAX = BigDecimal.valueOf(50_000);
    private static final BigDecimal PERSONAL_MIN = BigDecimal.valueOf(1_000);
    private static final BigDecimal BUSINESS_MAX = BigDecimal.valueOf(1_000_000);
    private static final BigDecimal BUSINESS_MIN = BigDecimal.valueOf(10_000);

    private static final String LESS_THEN_PERSONAL_MIN = "Please enter more money than " + PERSONAL_MIN;
    private static final String LESS_THEN_BUSINESS_MIN = "Too small sum for business credit. You should take personal credit";
    private static final String MORE_THEN_PERSONAL_MAX = String.format("Maximum credit money is %1$,.2f .\n May be you already have many credits", PERSONAL_MAX);
    private static final String MORE_THEN_BUSINESS_MAX = String.format("Maximum credit money is %1$,.2f .\n May be you already have many credits", BUSINESS_MAX);

    private final BalanceService balanceService;
    private final CreditRepository creditRepository;

    @Override
    public void validate(Credit credit) {
        val amount = credit.getInitialAmount();

        val maxLimit = creditRepository.findAllByAccountIdAndTypeIn(credit.getAccountId(), CREDITS).stream()
                .map(Credit::getInitialAmount)
                .reduce(credit.getInitialAmount(), BigDecimal::add);


        switch (credit.getType()) {
            case PERSONAL_CREDIT:
                if (amount.compareTo(PERSONAL_MIN) < 0) throw new ValidationException(LESS_THEN_PERSONAL_MIN);
                if (maxLimit.compareTo(PERSONAL_MAX) > 0) throw new ValidationException(MORE_THEN_PERSONAL_MAX);
                break;
            case BUSINESS_CREDIT:
                if (amount.compareTo(BUSINESS_MIN) < 0) throw new ValidationException(LESS_THEN_BUSINESS_MIN);
                if (maxLimit.compareTo(BUSINESS_MAX) > 0) throw new ValidationException(MORE_THEN_BUSINESS_MAX);
                break;
            case BUSINESS_DEPOSIT:
                if (amount.compareTo(balanceService.balance(credit.getAccountId())) > 0 ) throw new InsufficientFundsException();
        }
    }
}
