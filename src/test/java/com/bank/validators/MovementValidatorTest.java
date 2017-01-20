package com.bank.validators;

import com.bank.Application;
import com.bank.DataTest;
import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.domain.user.User;
import com.bank.service.BillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static com.bank.DataTest.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class MovementValidatorTest {

    @Autowired
    @Spy
    private MovementValidator validator;

    @Test
    public void validatePayTest(){
        String money = "30.0";
        ValidationResult<Double> result = validator.validatePay(jimmyBill, money);
        assertFalse(result.hasError());
        verify(validator, times(1)).validateMoney(money);
        verify(validator, times(1)).diffValidation(jimmyBill, Double.parseDouble(money));
    }

    @Test
    public void validateTransferTest()  {
        String money = "10.0";
        String uuid = bartBill.getUuid().toString();
        ValidationResult<MovementValidationAnswer> result = validator.validateTransfer(jimmyBill, uuid, money);
        assertFalse(result.hasError());
        verify(validator, times(1)).validateMoney(money);
        verify(validator, times(1)).validateBill(uuid);
        verify(validator, times(1)).diffValidation(jimmyBill, Double.parseDouble(money));
    }

    @Test
    public void validateBillTest(){
        assertTrue(validator.validateBill("invalid UUID").hasError());
        assertTrue(validator.validateBill(UUID.randomUUID().toString()).hasError());
        assertFalse(validator.validateBill(BillService.bankUUID.toString()).hasError());
    }

    @Test
    public void validateMoneyTest() {
        assertFalse(validator.validateMoney("30.0").hasError());
        assertFalse(validator.validateMoney("100").hasError());
        assertTrue(validator.validateMoney("30dd").hasError());
        assertTrue(validator.validateMoney("wtest").hasError());
    }

    @Test
    public void diffValidationTest() {
        Bill bill = new Bill(new Account(), UUID.randomUUID(), 40.0);
        assertTrue(validator.diffValidation(bill, 20.0));
        assertTrue(validator.diffValidation(bill, 40.0));
        assertFalse(validator.diffValidation(bill, 60.0));
    }

    @Test
    public void moneyHasErrorTest(){
        ValidationResult<MovementValidationAnswer> result
                = validator.validateTransfer(bartBill, jimmyBill.getUuid().toString(), "aaaa");
        assertTrue(result.hasError());
    }

    @Test
    public void incorrectUUIDTest(){
        ValidationResult<MovementValidationAnswer> result
                = validator.validateTransfer(bartBill, "30303 030303 030303", "" + 500.0);
        assertTrue(result.hasError());
        assertEquals(MovementValidator.INCORRECT_UUID, result.getError());
    }

    @Test
    public void notEnoughMoneyErrorTest(){
        ValidationResult<MovementValidationAnswer> result
                = validator.validateTransfer(bartBill,  jimmyBill.getUuid().toString(), "" + 1_000_000_000.0);
        assertTrue(result.hasError());
        assertEquals(MovementValidator.NOT_ENOUGH_MONEY, result.getError());
    }

    @Test
    public void transferTest(){
        Double money = 80.0;
        ValidationResult<MovementValidationAnswer> result
                = validator.validateTransfer(bartBill,  jimmyBill.getUuid().toString(), money.toString());
        assertFalse(result.hasError());
        assertEquals(jimmyBill, result.getEntity().getBill());
        assertEquals(money, result.getEntity().getMoney());
    }

    @Test
    public void payForNewBillTest(){
        assertTrue(validator.validatePayForNewBill(bartAcc));
    }

    @Test
    public void payTest(){
        Double money = 10.0;
        ValidationResult<Double> result = validator.validatePay(bartBill, money.toString());
        verify(validator, times(1)).validateMoney(money.toString());
        verify(validator,times(1)).diffValidation(bartBill, money);
    }

    @Test
    public void notEnoughMoneyForPayTest(){
        Double money = 10_000_000.0;
        ValidationResult<Double> result = validator.validatePay(bartBill, money.toString());
        assertEquals(MovementValidator.NOT_ENOUGH_MONEY, result.getError());
    }

    @Test
    public void createNewBill(){
        assertTrue(validator.validatePayForNewBill(aliceAcc));
    }

}