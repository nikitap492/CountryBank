package com.bank.validators;

import com.bank.domain.Account;
import com.bank.domain.user.User;
import org.junit.Test;

import static com.bank.validators.Validator.validateAccount;
import static com.bank.validators.Validator.validateEmail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValidatorTest {


    @Test
    public void emailValidatorTest() {
        assertTrue(validateEmail("test@test.te"));
        assertFalse(validateEmail("@test.te"));
        assertFalse(validateEmail("test@test"));
        assertFalse(validateEmail("test@.te"));
        assertTrue(validateEmail("tes22t@te22st.te"));
        assertFalse(validateEmail("testtest.te"));
        assertFalse(validateEmail("t!222@test.te"));
        assertFalse(validateEmail("test@te#st.te"));
        assertTrue(validateEmail("te.st@test.te"));
        assertTrue(validateEmail("TeSt@test.te"));
        assertFalse(validateEmail("test@test.3"));
    }


    @Test
    public void accountValidatorTest() {
        Account test1 = new Account(new User(), "name", "");
        assertFalse(validateAccount(test1));
        Account test2 = new Account(new User(), "", "address");
        assertFalse(validateAccount(test2));
        Account test3 = new Account(new User(), "name", "address");
        assertTrue(validateAccount(test3));
    }

}
