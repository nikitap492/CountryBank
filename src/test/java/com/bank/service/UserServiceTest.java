package com.bank.service;

import com.bank.Application;
import com.bank.domain.Account;
import com.bank.domain.other.RegistrationToken;
import com.bank.domain.other.ResetPasswordToken;
import com.bank.domain.user.User;
import com.bank.domain.user.UserRegister;
import com.bank.repositories.RegistrationTokenRepository;
import com.bank.repositories.ResetPasswordTokenRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static com.bank.DataTest.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void shouldAddUser() {
        String john = "John";
        User user = new User(john, john, john + "@test.test");
        userService.save(user);
        UserDetails found = userService.loadUserByUsername(john);
        assertEquals(user, found);
    }

    @Test
    public void shouldAddUserAndAccountByUserRegister() {
        String jessie = "Jessie";
        UserRegister userRegister = new UserRegister(jessie, jessie, jessie + "@test.us", jessie, jessie + "_home");
        userService.save(userRegister);
        User user = userService.findByUsername(jessie);
        assertEquals(userRegister.getUser(), user);
        Account account = accountService.findByUsername(jessie);
        assertEquals(userRegister.getAccount(), account);
    }


    @Test
    public void shouldFindUserByName() {
        UserDetails found = userService.loadUserByUsername(kurt);
        assertEquals(kurtUser, found);
    }

    @Test
    public void shouldFindUserByEmail() {
        User found = (User) userService.loadUserByUsername(susieUser.getEmail());
        assertNotNull(found);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExceptionUsernameNotFound() {
        userService.loadUserByUsername("Exception");
    }

    @Test
    public void shouldFindByEmailAndUsername() {
        boolean byUsername = userService.findByUsernameOrEmail(kurtUser.getUsername());
        boolean byEmail = userService.findByUsernameOrEmail(kurtUser.getEmail());
        assertTrue(byEmail);
        assertTrue(byUsername);
    }

    @Test
    public void shouldCreateResetPasswordToken() {
        ResetPasswordToken token = userService.createResetPasswordToken(susieUser.getEmail());
        ResetPasswordToken found = userService.findResetPasswordToken(token.getToken());
        assertEquals(token, found);
    }

    @Test
    public void shouldCreateRegistrationToken() {
        RegistrationToken token = userService.createRegistrationToken(susieUser);
        RegistrationToken found = userService.findRegistrationToken(token.getToken());
        assertEquals(token, found);
    }

    @Test
    public void shouldResetPassword() {
        String new_pass = "new_test_password";
        ResetPasswordToken token = userService.createResetPasswordToken(susieUser.getEmail());
        userService.resetPassword(token, new_pass);
        assertFalse(token.getValidity());
        assertTrue(encoder.matches(new_pass, token.getUser().getPassword()));
    }

    @Test
    public void shouldSetEnabled() {
        RegistrationToken token = userService.createRegistrationToken(susieUser);
        userService.setEnabled(token);
        assertTrue(token.getUser().isEnabled());
        assertFalse(token.getValidity());
    }

    @Test
    public void shouldCheckTokens(){
        // To make sure that all tokens is not validity
        userService.checkTokens();
        LocalDateTime time = LocalDateTime.now().minusDays(2);
        RegistrationToken t1 = new RegistrationToken(susieUser, time);
        RegistrationToken t2 = new RegistrationToken(bartUser, time);
        ResetPasswordToken t3 = new ResetPasswordToken(kurtUser, time);
        registrationTokenRepository.save(t1);
        registrationTokenRepository.save(t2);
        resetPasswordTokenRepository.save(t3);
        int updatedTokens = userService.checkTokens();
        assertFalse(userService.findResetPasswordToken(t3.getToken()).getValidity());
        assertFalse(userService.findRegistrationToken(t2.getToken()).getValidity());
        assertFalse(userService.findRegistrationToken(t1.getToken()).getValidity());
        assertEquals(3, updatedTokens);
    }

}
