package com.bank.validators;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({CreditValidatorTest.class, MessageValidatorTest.class,
        MovementValidatorTest.class, UserValidatorTest.class,
        ValidatorTest.class, TokenValidatorTest.class})
public class ValidatorSuiteTest {

}