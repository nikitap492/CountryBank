package com.bank.validators;

import com.bank.domain.Bill;
import com.bank.domain.services.credit.Credit;
import com.bank.domain.services.credit.CreditFrequency;
import com.bank.domain.services.credit.CreditType;
import com.bank.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bank.validators.Validator.validateDouble;

@Component
public class CreditValidator {

    /**
     * Constants of personal and business crediting.
     */
    static final double PERSONAL_MAX = 500d;
    static final double PERSONAL_MIN = 20d;
    static final double BUSINESS_MAX = 2300d;
    static final double BUSINESS_MIN = 500d;
    /**
     * Credit maximum in month
     */
    static final int MAX_MONTHS = 40;
    /**
     * Errors
     */
    static final String INCORRECT_WITHDRAWS =  "Incorrect input of withdraws";
    static final String INCORRECT_TYPE= "Incorrect type of credit";
    static final String NOT_ENOUGH_MONEY = "You do not have enough money";
    static final String LESS_THEN_PERSONAL_MIN = "Please enter more money than " + PERSONAL_MIN;
    static final String LESS_THEN_BUSINESS_MIN = "Too small sum for business credit. You should take personal credit";
    static final String MORE_THEN_PERSONAL_MAX = String.format("Maximum credit money is %1$,.2f .\n May be you already have many credits", PERSONAL_MAX);
    static final String MORE_THEN_BUSINESS_MAX = String.format("Maximum credit money is %1$,.2f .\n May be you already have many credits", BUSINESS_MAX);
    static final String MORE_THEN_MAX_MONTHS = "Maximum of months is " + MAX_MONTHS;


    @Autowired
    private CreditService service;

    /**
     * Validation that num is able to transform into {@link Integer} and value less then {@value MAX_MONTHS}
     *
     * @param num is number of withdraws
     * @return generic entity {@link ValidationResult<Integer>} of number of withdraws
     */
    ValidationResult<Integer> validateNumOfWithDraws(String num) {
        ValidationResult<Integer> result = new ValidationResult<>();
        try {
            int i = Integer.parseInt(num);
            if (i > MAX_MONTHS)
                result.setError(MORE_THEN_MAX_MONTHS);
            else result.setEntity(i);
        } catch (IllegalArgumentException e) {
            result.setError(INCORRECT_WITHDRAWS);
        }
        return result;
    }

    /**
     * Validation what type is correct {@link Integer} value and
     *
     * @param type must be between 0 and 2
     * @return {@link ValidationResult<CreditType>}
     */
    ValidationResult<CreditType> validateCreditType(String type) {
        ValidationResult<CreditType> result = new ValidationResult<>();
        try {
            Integer i = Integer.parseInt(type);
            result.setEntity(CreditType.getType(i));
        } catch (IllegalArgumentException e) {
            result.setError(INCORRECT_TYPE);
        }
        return result;
    }

    /**
     * {@link Double} type validation for money
     */
    ValidationResult<Double> validateMoney(String money) {
        return validateDouble(money, "money");
    }

    /**
     * Union validation for {@link Credit}
     * Firstly, take a place a validation {@code validateMoney} for
     *
     * @param money Secondly, validation {@code validateNumOfWithDraws} for
     * @param type  and {@code validateCreditType} for
     * @param num   After all there are checking sum by all account's credits is less than a {@value PERSONAL_MAX}
     *              or {@value BUSINESS_MAX} and credit money is greater than
     *              {@value PERSONAL_MIN} or {@value BUSINESS_MIN}
     *              for personal and business credit.
     *              And for deposit method checks what is credit has enough money for transaction
     * @return {@link ValidationResult<Credit>}
     */
    public ValidationResult<Credit> validate(Bill bill, String num, String money, String type) {
        ValidationResult<Credit> credit = new ValidationResult<>();
        ValidationResult<Double> m = validateMoney(money);
        if (m.hasError()) {
            credit.setError(m.getError());
            return credit;
        }
        ValidationResult<Integer> withDraws = validateNumOfWithDraws(num);
        if (withDraws.hasError()) {
            credit.setError(withDraws.getError());
            return credit;
        }
        ValidationResult<CreditType> creditType = validateCreditType(type);
        if (creditType.hasError()) {
            credit.setError(creditType.getError());
            return credit;
        }
        switch (creditType.getEntity()) {
            case CREDIT_FOR_PERSONAL:
                if (m.getEntity() < PERSONAL_MIN) credit.setError(LESS_THEN_PERSONAL_MIN);
                else if (service.sumAllCreditsByAccount(bill.getAccount()) > PERSONAL_MAX || m.getEntity() > PERSONAL_MAX)
                    credit.setError(MORE_THEN_PERSONAL_MAX);
                break;
            case CREDIT_FOR_BUSINESS:
                if (m.getEntity() < BUSINESS_MIN)
                    credit.setError(LESS_THEN_BUSINESS_MIN);
                if (service.sumAllCreditsByAccount(bill.getAccount()) > BUSINESS_MAX || m.getEntity() > BUSINESS_MAX)
                    credit.setError(MORE_THEN_BUSINESS_MAX);
                break;
            case DEPOSIT_FOR_BUSINESS:
                if (m.getEntity() > bill.getMoney()) {
                    credit.setError(NOT_ENOUGH_MONEY);
                }
        }
        if (!credit.hasError()) {
            credit.setEntity(Credit.CreditBuilder
                    .of(bill, CreditFrequency.MONTH, creditType.getEntity(), m.getEntity(), withDraws.getEntity()));
        }
        return credit;
    }

}
