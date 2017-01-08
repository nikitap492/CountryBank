package com.bank.validators;

import com.bank.Application;
import com.bank.DataTest;
import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.service.BillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static com.bank.DataTest.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Poshivalov Nikita
 * @since 08.12.2016.
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
public class MovementValidatorTest {

    @Autowired
    @Spy
    private MovementValidator validator;

    @Test
    public void validatePayTest() throws Exception {
        String money = "30.0";
        ValidationResult<Double> result = validator.validatePay(jimmyBill, money);
        assertFalse(result.hasError());
        verify(validator, times(1)).validateMoney(money);
        verify(validator, times(1)).diffValidation(jimmyBill, Double.parseDouble(money));
    }

    @Test
    public void validateTransferTest() throws Exception {
        String money = "10.0";
        String uuid = bartBill.getUuid().toString();
        ValidationResult<MovementValidationAnswer> result = validator.validateTransfer(jimmyBill, uuid, money);
        assertFalse(result.hasError());
        verify(validator, times(1)).validateMoney(money);
        verify(validator, times(1)).validateBill(uuid);
        verify(validator, times(1)).diffValidation(jimmyBill, Double.parseDouble(money));
    }

    @Test
    public void validateBillTest() throws Exception {
        assertTrue(validator.validateBill("invalid UUID").hasError());
        assertTrue(validator.validateBill(UUID.randomUUID().toString()).hasError());
        assertFalse(validator.validateBill(BillService.bankUUID.toString()).hasError());
    }

    @Test
    public void validateMoneyTest() throws Exception {
        assertFalse(validator.validateMoney("30.0").hasError());
        assertFalse(validator.validateMoney("100").hasError());
        assertTrue(validator.validateMoney("30dd").hasError());
        assertTrue(validator.validateMoney("wtest").hasError());
    }

    @Test
    public void diffValidationTest() throws Exception {
        Bill bill = new Bill(new Account(), UUID.randomUUID(), 40.0);
        assertTrue(validator.diffValidation(bill, 20.0));
        assertTrue(validator.diffValidation(bill, 40.0));
        assertFalse(validator.diffValidation(bill, 60.0));
    }

}