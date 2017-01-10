package com.bank.validators;

import com.bank.Application;
import com.bank.domain.Account;
import com.bank.domain.other_services.Message;
import com.bank.domain.user.User;
import com.bank.domain.user.UserRegister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bank.validators.Validator.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


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
