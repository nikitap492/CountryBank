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

import static com.bank.validators.UserValidator.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class UserValidatorTest {

    @Autowired @Spy
    private UserValidator userValidator;
    private static final String NAME = "tester";
    private static final String EMAIL = "test@email.test";

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
        UserRegister ur = wrapper(new User(NAME, NAME, EMAIL));
        userValidator.validate(ur);
        verify(userValidator, times(1)).validatePassword(NAME);
        verify(userValidator, times(1)).validateUsername(NAME);
        verify(userValidator, times(1)).isUserNotExist(NAME);
        verify(userValidator, times(1)).isUserNotExist(EMAIL);
    }

    @Test
    public void nullUsernameTest(){
        User user = new User(null, null, null);
        String ans = userValidator.validate(wrapper(user));
        assertEquals(NULL, ans);
    }

    @Test
    public void smallPasswordTest(){
        User user = new User(NAME, "t", EMAIL);
        String ans = userValidator.validate(wrapper(user));
        assertEquals(SMALL_PASSWORD, ans);
    }

    @Test
    public void incorrectUsernameTest(){
        User user = new User("!@GgdhgSSa23!2", NAME, EMAIL);
        String ans = userValidator.validate(wrapper(user));
        assertEquals(INCORRECT_USERNAME, ans);
    }

    @Test
    public void userAlreadyExistTest(){
        User user = new User("government", NAME, EMAIL);
        String ans = userValidator.validate(wrapper(user));
        assertEquals(USERNAME_ALREADY_EXIST, ans);
    }

    @Test
    public void incorrectEmailTest(){
        User user = new User(NAME, NAME, "Incorrect@email@com");
        String ans = userValidator.validate(wrapper(user));
        assertEquals(INCORRECT_EMAIL, ans);
    }

    @Test
    public void emailAlreadyExistTest(){
        User user = new User(NAME, NAME, "gov@gov.gov");
        String ans = userValidator.validate(wrapper(user));
        assertEquals(EMAIL_ALREADY_EXIST, ans);
    }

    @Test
    public void smallAddressOrNameExistTest(){
        User user = new User(NAME, NAME, EMAIL);
        UserRegister us = new UserRegister(user, new Account(user, "", ""));
        String ans = userValidator.validate(us);
        assertEquals(SMALL_ADDRESS_OR_NAME, ans);
    }

    private UserRegister wrapper(User user){
        return new UserRegister(user, new Account(user, NAME, NAME));
    }
}
