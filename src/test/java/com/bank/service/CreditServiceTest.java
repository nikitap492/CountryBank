package com.bank.service;

import com.bank.Application;
import com.bank.domain.Bill;
import com.bank.domain.services.credit.Credit;
import com.bank.repositories.BillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.sql.Date;
import java.util.ArrayList;

import static com.bank.domain.services.credit.Credit.CreditBuilder.*;
import static com.bank.domain.services.credit.CreditFrequency.*;
import static com.bank.domain.services.credit.CreditState.*;
import static com.bank.domain.services.credit.CreditType.*;
import static com.bank.DataTest.*;
import static java.time.LocalDate.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class CreditServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CreditServiceTest.class);

    @Autowired
    @Spy
    private CreditService creditService;

    @Autowired
    private BillRepository billRepository;


    @Test
    public void shouldFindByIdAndSetNewStateAndUpdateDate() {
        Credit credit = of(bartBill, WEAK, CREDIT_FOR_PERSONAL, 200.0, now().minusDays(8),
                now().minusDays(8), OPENED, now(), 1);
        creditService.save(credit);
        creditService.updateStateAndDate(credit);
        assertTrue(creditService.findById(credit.getId()).getState() == CLOSED);
        assertEquals(Date.valueOf(now()), creditService.findById(credit.getId()).getLastUpdateDate());
    }

    @Test
    public void shouldCheckDateRight() {
        Credit credit1 = of(jimmyBill, WEAK, CREDIT_FOR_PERSONAL, 200.0, now().minusDays(6),
                now().minusDays(6), OPENED, now().plusWeeks(4), 1);
        Credit credit2 = of(jimmyBill, WEAK, CREDIT_FOR_PERSONAL, 200.0, now().minusDays(8),
                now().minusDays(8), OPENED, now().plusWeeks(4), 1);
        Credit credit3 = of(jimmyBill, MONTH, CREDIT_FOR_PERSONAL, 200.0, now().minusWeeks(5),
                now().minusWeeks(5), OPENED, now().plusMonths(4), 1);
        Credit credit4 = of(jimmyBill, MONTH, CREDIT_FOR_PERSONAL, 200.0, now().minusWeeks(3),
                now().minusWeeks(3), OPENED, now().plusMonths(4), 1);
        assertFalse(creditService.checkDate(credit1));
        assertTrue(creditService.checkDate(credit2));
        assertTrue(creditService.checkDate(credit3));
        assertFalse(creditService.checkDate(credit4));
    }

    @Test
    public void shouldSaveCredit() throws Exception {
        Double creditMoney = 300.0;
        Double sum = bartBill.getMoney() + creditMoney;
        Credit credit = of(bartBill, WEAK, CREDIT_FOR_PERSONAL, creditMoney, 5);
        creditService.save(credit);
        assertEquals(sum, bartBill.getMoney());
    }

    @Test
    public void shouldSaveDeposit() throws Exception {
        Double creditMoney = 300.0;
        Double sum = bartBill.getMoney() + creditMoney;
        Credit credit = of(bartBill, WEAK, CREDIT_FOR_PERSONAL, creditMoney, 5);
        creditService.save(credit);
        assertEquals(sum, bartBill.getMoney());
    }

    @Test
    public void shouldCountCreditSumForAccount() {
        Bill bill1 = new Bill(susieAcc, 0.0);
        Bill bill2 = new Bill(susieAcc, 0.0);
        billRepository.save(bill1);
        billRepository.save(bill2);
        Credit credit1 = Credit.CreditBuilder.of(bill1, MONTH, CREDIT_FOR_PERSONAL, 250.0, 4);
        Credit credit2 = Credit.CreditBuilder.of(bill2, MONTH, CREDIT_FOR_PERSONAL, 250.0, 4);
        creditService.save(credit1);
        creditService.save(credit2);
        double sum = creditService.sumAllCreditsByAccount(susieAcc);
        log.debug("Sum: " + sum);
        assertEquals(500.0, sum, 0.001);
    }

    @Test
    public void shouldCheck() {
        Credit deposit = Credit.CreditBuilder.of(jimmyBill, MONTH, DEPOSIT_FOR_BUSINESS, 1000.0, now().minusMonths(2),
                now().minusWeeks(5), OPENED, now().plusMonths(2), 4);
        Credit credit = Credit.CreditBuilder.of(jimmyBill, MONTH, CREDIT_FOR_PERSONAL, 100.0, now().minusMonths(2),
                now().minusWeeks(5), OPENED, now().plusMonths(2), 4);
        creditService.save(credit);
        creditService.save(deposit);
        creditService.check();
        verify(creditService, times(1)).checkDate(credit);
        verify(creditService, times(1)).checkDate(deposit);
    }

}