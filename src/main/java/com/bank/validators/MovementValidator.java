package com.bank.validators;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovementValidator {

    private static final Logger log = LoggerFactory.getLogger(MovementValidator.class);

    @Autowired
    private BillService service;

    static final String INCORRECT_UUID = "You have inputted incorrect bill's UUID";
    static final String NOT_ENOUGH_MONEY = "Your have not enough money for this transaction";
    private static final String BILL_NOT_EXIST = "Bill was not found by UUID";

    /**
     * @param current is current bill of account
     *                Methods validateResetPasswordToken {@code validateMoney} for
     * @param money   is less or equal than @param current has
     * @return {@link ValidationResult<Double>}
     */
    public ValidationResult<Double> validatePay(Bill current, String money) {
        ValidationResult<Double> result = validateMoney(money);
        if (!result.hasError() && !diffValidation(current, result.getEntity())) {
            result.setError(NOT_ENOUGH_MONEY);
            log.debug(NOT_ENOUGH_MONEY + " for bill " + current);
        }
        return result;
    }

    /**
     * @param account needs for obtaining current bill
     */
    public boolean validatePayForNewBill(Account account) {
        try {
            Bill current = service.getCurrentForAccount(account);
            return Double.compare(current.getMoney(), 5.0) >= 0;
        } catch (NullPointerException e) {
            return true; //First bill for account
        }
    }

    /**
     * In the beginning validateResetPasswordToken {@code  validateMoney} {@code  validateBill}
     * after if validation has no errors then  {@code   diffValidation}
     *
     * @return {@link ValidationResult<MovementValidationAnswer>}
     */
    public ValidationResult<MovementValidationAnswer> validateTransfer(Bill current, String uuid, String money) {
        ValidationResult<MovementValidationAnswer> result = new ValidationResult<>();
        ValidationResult<Double> moneyResult = validateMoney(money);
        ValidationResult<Bill> billResult = validateBill(uuid);
        if (moneyResult.hasError()) {
            result.setError(moneyResult.getError());
            log.debug("Money error : " + moneyResult.getError());
        } else if (billResult.hasError()) {
            result.setError(billResult.getError());
            log.debug("Bill error : " + billResult.getError());
        } else {
            Double m = moneyResult.getEntity();
            if (diffValidation(current, m)) {
                result.setEntity(new MovementValidationAnswer(billResult.getEntity(), m));
                log.debug("Data have no errors");
            } else {
                result.setError(NOT_ENOUGH_MONEY);
                log.debug(NOT_ENOUGH_MONEY + " for bill " + current);
            }
        }
        return result;
    }

    /**
     * Method trying to find bill by
     *
     * @param uuid is bill identifier
     * @return {@link ValidationResult<Bill>}
     */
    ValidationResult<Bill> validateBill(String uuid) {
        ValidationResult<Bill> result = new ValidationResult<>();
        try {
            Bill bill = service.findByUuid(uuid);
            if (bill != null) {
                result.setEntity(bill);
            } else result.setError(BILL_NOT_EXIST);
        } catch (IllegalArgumentException e) {
            result.setError(INCORRECT_UUID);
        }
        return result;
    }

    ValidationResult<Double> validateMoney(String money) {
        return Validator.validateDouble(money, "money");
    }

    /**
     * Method check whether bill enough money for transaction (movement)
     *
     * @param bill  is current bill of account
     * @param money for transaction
     */

    boolean diffValidation(Bill bill, Double money) {
        return bill.getMoney() - money >= 0;
    }
}
