package com.bank.validators;

import com.bank.Application;
import com.bank.domain.other.RegistrationToken;
import com.bank.domain.other.ResetPasswordToken;
import com.bank.repositories.ResetPasswordTokenRepository;
import com.bank.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static com.bank.DataTest.bartUser;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class TokenValidatorTest {

    @Autowired
    private TokenValidator validator;

    @Autowired
    private UserService service;

    @Autowired
    private ResetPasswordTokenRepository repository;


    @Test
    public void notFoundTest(){
        ValidationResult<RegistrationToken> result = validator.validateRegistrationToken("Not found");
        assertTrue(result.hasError());
        assertEquals(TokenValidator.NOT_FOUND, result.getError());
    }

    @Test
    public void notValidityTest(){
        ResetPasswordToken token = service.createResetPasswordToken(bartUser.getUsername());
        service.setNonValidityToken(token);
        ValidationResult<ResetPasswordToken> result = validator.validateResetPasswordToken(token.getToken());
        assertTrue(result.hasError());
        assertEquals(TokenValidator.NOT_VALIDITY, result.getError());
    }

    @Test
    public void tokenTest(){
        ResetPasswordToken token = new ResetPasswordToken(bartUser, LocalDateTime.now().minusHours(14));
        repository.save(token);
        ValidationResult<ResetPasswordToken> result = validator.validateResetPasswordToken(token.getToken());
        assertFalse(result.hasError());
        assertNotNull(result.getEntity());
        token = new ResetPasswordToken(bartUser, LocalDateTime.now());
        repository.save(token);
        result = validator.validateResetPasswordToken(token.getToken());
        assertFalse(result.hasError());
        assertNotNull(result.getEntity());

    }

    @Test
    public void notValidityAnymoreTest(){
        ResetPasswordToken token = new ResetPasswordToken(bartUser, LocalDateTime.now().minusDays(2));
        repository.save(token);
        ValidationResult<ResetPasswordToken> result = validator.validateResetPasswordToken(token.getToken());
        assertTrue(result.hasError());
        assertEquals(TokenValidator.NOT_VALIDITY, result.getError());
    }

}
