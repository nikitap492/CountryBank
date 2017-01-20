package com.bank.validators;

import com.bank.Application;
import com.bank.domain.services.credit.Credit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bank.DataTest.bartBill;
import static com.bank.domain.services.credit.CreditType.*;
import static com.bank.validators.CreditValidator.*;
import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class CreditValidatorTest {


    @Autowired
    private CreditValidator validator;

    @Test
    public void mumOfWithDrawsTest() throws Exception {
        assertTrue(!validator.validateNumOfWithDraws("2").hasError());
        assertTrue(validator.validateNumOfWithDraws("as2").hasError());
        assertTrue(validator.validateNumOfWithDraws("2.2").hasError());
    }

    @Test
    public void creditTypeTest() throws Exception {
        assertTrue(!validator.validateCreditType("0").hasError());
        assertTrue(!validator.validateCreditType("1").hasError());
        assertTrue(!validator.validateCreditType("2").hasError());
        assertTrue(validator.validateCreditType("3").hasError());
        assertTrue(validator.validateCreditType("d").hasError());
    }

    @Test
    public void moneyTest(){
        Double money = 70.50;
        ValidationResult<Double> result = validator.validateMoney(money.toString());
        assertFalse(result.hasError());
        assertEquals(money, result.getEntity());
    }

    @Test
    public void incorrectMoneyInputTest(){
        String money = "7qs0vsz.50";
        ValidationResult<Double> result = validator.validateMoney(money);
        assertTrue(result.hasError());
        assertEquals("Incorrect money input", result.getError());
    }

    @Test
    public void incorrectTypeTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "5", "20.0", "aa");
        assertTrue(result.hasError());
        assertEquals(INCORRECT_TYPE, result.getError());
    }

    @Test
    public void incorrectWithdrawsTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "asdf", "20.0", "1");
        assertTrue(result.hasError());
        assertEquals(INCORRECT_WITHDRAWS, result.getError());
    }

    @Test
    public void incorrectMoneyTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "4", "Incorrect", "1");
        assertTrue(result.hasError());
        assertEquals("Incorrect money input", result.getError());
    }

    @Test
    public void moreThenBusinessMaxTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "4",
                String.valueOf(BUSINESS_MAX + 1000.0), String.valueOf(CREDIT_FOR_BUSINESS.getType()));
        assertTrue(result.hasError());
        assertEquals(MORE_THEN_BUSINESS_MAX, result.getError());
    }

    @Test
    public void lessThenBusinessMinTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "4",
                String.valueOf(BUSINESS_MIN - 100.0), String.valueOf(CREDIT_FOR_BUSINESS.getType()));
        assertTrue(result.hasError());
        assertEquals(LESS_THEN_BUSINESS_MIN, result.getError());
    }

    @Test
    public void lessThenPersonalMinTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "4",
                String.valueOf(PERSONAL_MIN - 5.0), String.valueOf(CREDIT_FOR_PERSONAL.getType()));
        assertTrue(result.hasError());
        assertEquals(LESS_THEN_PERSONAL_MIN, result.getError());
    }

    @Test
    public void moreThenPersonalMaxTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "4",
                String.valueOf(PERSONAL_MAX + 100.0), String.valueOf(CREDIT_FOR_PERSONAL.getType()));
        assertTrue(result.hasError());
        assertEquals(MORE_THEN_PERSONAL_MAX, result.getError());
    }


    @Test
    public void notEnoughMoneyTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "4",
                String.valueOf(bartBill.getMoney() + 100.0), String.valueOf(DEPOSIT_FOR_BUSINESS.getType()));
        assertTrue(result.hasError());
        assertEquals(NOT_ENOUGH_MONEY, result.getError());
    }


    @Test
    public void creditTest(){
        ValidationResult<Credit> result = validator.validate(bartBill, "4",
                String.valueOf(bartBill.getMoney() - 20.0), String.valueOf(DEPOSIT_FOR_BUSINESS.getType()));
        assertFalse(result.hasError());
        assertNotNull(result.getEntity());
    }

    @Test
    public void moreThenMaxMonthsTest(){
        ValidationResult<Credit> result = validator.validate(bartBill,  String.valueOf(MAX_MONTHS + 5),
                String.valueOf(bartBill.getMoney() + 100.0), String.valueOf(DEPOSIT_FOR_BUSINESS.getType()));
        assertTrue(result.hasError());
        assertEquals(MORE_THEN_MAX_MONTHS, result.getError());
    }
}