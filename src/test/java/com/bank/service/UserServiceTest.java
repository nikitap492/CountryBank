package com.bank.service;

import com.bank.Application;
import com.bank.domain.user.User;
import com.bank.domain.user.UserRegister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bank.DataTest.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    @Spy
    private UserService userService;

    @Autowired
    private AccountService accountService;


    @Test
    public void shouldAddUser() {
        String john = "John Smith";
        User user = new User(john, john, john + "@test.test");
        userService.save(user);
        UserDetails found = userService.loadUserByUsername(john);
        assertEquals(user, found);
    }

    @Test
    public void shouldAddUserAndAccountByUserRegister() {
        String jessie = "Jessie Dill";
        UserRegister userRegister = new UserRegister(jessie, jessie, jessie + "@test.us", jessie, jessie + "_home");
        userService.save(userRegister);
        verify(userService, times(1)).save(userRegister.getUser());
    }


    @Test
    public void shouldFindUserByName() {
        UserDetails found = userService.loadUserByUsername(kurt);
        assertEquals(kurtUser, found);
    }

    @Test
    public void shouldFindByEmail() {
        UserDetails found = userService.loadUserByUsername(susieUser.getEmail());
        assertEquals(susieUser, found);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void usernameNotFound() {
        userService.loadUserByUsername("Exception");
    }

    @Test
    public void shouldFindbyEmailAndUsername() {
        boolean byUsername = userService.findByUsernameOrEmail(kurtUser.getUsername());
        boolean byEmail = userService.findByUsernameOrEmail(kurtUser.getEmail());
        assertTrue(byEmail);
        assertTrue(byUsername);
    }

}
