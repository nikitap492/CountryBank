package com.bank.service.impl;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.domain.services.credit.Credit;
import com.bank.domain.services.credit.CreditState;
import com.bank.repositories.CreditRepository;
import com.bank.service.BillService;
import com.bank.service.CreditService;
import com.bank.service.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of {@link CreditService}
 */

@Service
public class CreditServiceImpl implements CreditService {

    private static final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

    private static Bill bankBill;
    private static final Jsr310JpaConverters.LocalDateConverter dateConverter
            = new Jsr310JpaConverters.LocalDateConverter();

    @Autowired
    private CreditRepository repository;

    @Autowired
    private MovementService movementService;

    @Autowired
    private BillService billService;

    /**
     * @return bank bill
     */
    private Bill getBankBill() {
        if (bankBill == null) {
            bankBill = billService.findByUuid(BillService.bankUUID);
        }
        return bankBill;
    }

    /**
     * Scheluded method for credit checking. Cron is defined in classpath:Application.yml
     * Method looks for credits in state "OPENED", after for each invokes method {@code checkDate(credit)}.
     * if credit is not updated, then {@code updateStateAndDate(credit)} is called.
     */
    @Override
    @Scheduled(cron = "${schedule.cron}")
    public void check() {
        log.debug("Scheduled credit checking");
        List<Credit> credits = repository.findByState(CreditState.OPENED);
        for (Credit credit : credits) {
            if (!checkDate(credit)) continue;
            // Credit needs to updating
            double money = credit.getMoney() * credit.getType().getPer() / credit.getNumOfWithdraws();
            switch (credit.getType()) {
                case DEPOSIT_FOR_BUSINESS:
                    /**Make new income for {@code credit.getBill()}*/
                    movementService.makeTransfer(credit.getBill(), bankBill, money, credit.getFrequency().getFreq() + " payment according to deposit contact");
                    break;
                case CREDIT_FOR_PERSONAL:
                case CREDIT_FOR_BUSINESS:
                    /**Make new expense for {@code credit.getBill()}*/
                    movementService.makeTransfer(bankBill, credit.getBill(), money, credit.getFrequency().getFreq() + " payment according to credit contact");
                    break;
            }
            //Set new date and state
            updateStateAndDate(credit);
        }
    }

    /**
     * {@code updateStateAndDate(credit)} will update {@param credit}'s lastUpdateDate and might be state.
     */
    @Override
    public void updateStateAndDate(Credit credit) {
        log.debug("Set new date of updating for " + credit);
        repository.save(credit.setLastUpdateDate());
    }

    /**
     * Method returns checking state.
     * If {@return true} then credit'll be updated
     */
    @Override
    public boolean checkDate(Credit credit) {
        //Get current date
        LocalDate now = LocalDate.now();

        LocalDate creditDate = dateConverter.convertToEntityAttribute(credit.getLastUpdateDate());
        //Temporary variable keeps days difference.
        LocalDate tmp = now.minusYears(creditDate.getYear()).minusDays(creditDate.getDayOfYear());
        switch (credit.getFrequency()) {
            case MONTH:
                //if more then month
                if (tmp.getDayOfYear() >= now.lengthOfMonth()) return true;
                break;
            case WEAK:
                //if more then weak
                if (tmp.getDayOfYear() >= 7) return true;
        }
        return false;
    }

    /**
     * Method for saving {@param credit}
     */
    @Override
    public void save(Credit credit) {
        //Checking credit type
        switch (credit.getType()) {
            case DEPOSIT_FOR_BUSINESS:
                // Expense for deposit
                movementService.makeTransfer(getBankBill(), credit.getBill(), credit.getMoney(), "Deposit has opened");
                break;
            case CREDIT_FOR_PERSONAL:
            case CREDIT_FOR_BUSINESS:
                // Incomes for credit
                movementService.makeTransfer(credit.getBill(), getBankBill(), credit.getMoney(), "Credit has opened");
                break;
        }
        repository.save(credit);
        log.debug(credit + "has been saved");
    }

    @Override
    public Credit findById(Long id) {
        return repository.findOne(id);
    }

    /**
     * Checking for {@param account}, which can have few bill.
     * There are needs, because user could open bill for 5$ and take a credit on MAX, then open and take one more and e.g.
     * This validation  returns credits sum.
     */
    @Override
    public double sumAllCreditsByAccount(Account account) {
        Double d = repository.sumAllCreditsByAccount(account);
        return d == null ? 0 : d;
    }

}
