package com.bank.validators;

import com.bank.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Poshivalov Nikita
 * @since 13.12.2016.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class CreditValidatorTest {


    @Autowired
    private CreditValidator validator;

    @Test
    public void validateNumOfWithDraws() throws Exception {
        assertTrue(!validator.validateNumOfWithDraws("2").hasError());
        assertTrue(validator.validateNumOfWithDraws("as2").hasError());
        assertTrue(validator.validateNumOfWithDraws("2.2").hasError());
    }

    @Test
    public void validateCreditType() throws Exception {
        assertTrue(!validator.validateCreditType("0").hasError());
        assertTrue(!validator.validateCreditType("1").hasError());
        assertTrue(!validator.validateCreditType("2").hasError());
        assertTrue(validator.validateCreditType("3").hasError());
        assertTrue(validator.validateCreditType("d").hasError());
    }

}