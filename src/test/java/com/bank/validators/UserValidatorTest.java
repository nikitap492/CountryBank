package com.bank.validators;

import com.bank.Application;
import com.bank.domain.Account;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Poshivalov Nikita
 * @since 08.12.2016.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class UserValidatorTest {

    @Autowired
    @Spy
    private UserValidator userValidator;

    @Test
    public void usernameValidatorTest() {
        assertFalse(userValidator.validateUsername("2233333"));
        assertTrue(userValidator.validateUsername("test332"));
        assertFalse(userValidator.validateUsername("t2"));
    }

    @Test
    public void passwordValidatorTest() {
        assertFalse(userValidator.validatePassword("QWE"));
        assertTrue(userValidator.validatePassword("qwerty"));
    }

    @Test
    public void userValidatorTest() {
        String name = "tester";
        String email = "test@email.test";
        User test = new User(name, name, email);
        Account account = new Account(test, name, name);
        UserRegister ur = new UserRegister(test, account);
        userValidator.validate(ur);
        verify(userValidator, times(1)).validatePassword(name);
        verify(userValidator, times(1)).validateUsername(name);
        verify(userValidator, times(1)).isUserNotExist(name);
        verify(userValidator, times(1)).isUserNotExist(email);
    }
}
