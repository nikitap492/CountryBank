package com.bank.repositories;

import com.bank.Application;
import com.bank.domain.security.RememberMeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class RememberMeTokenRepositoryTest {

    @Autowired
    private RememberMeTokenRepository repository;

    @Test
    public void shouldCreateNewToken(){
        RememberMeToken token = getTestToken();
        PersistentRememberMeToken persistentToken = token.getPersistentToken();
        repository.createNewToken(persistentToken);
        RememberMeToken found = repository.findOne(token.getSeries());
        assertEquals(token, found);
    }

    @Test
    public void shouldUpdate(){
        RememberMeToken token = getTestToken();
        repository.save(token);
        Date date = new Date(System.nanoTime());
        repository.updateToken(token.getSeries(), token.getTokenValue(), date);
        RememberMeToken found = repository.findOne(token.getSeries());
        assertEquals(date, found.getDate());
    }

    @Test
    public void shouldGetTokenForSeries(){
        RememberMeToken token = getTestToken();
        repository.save(token);
        PersistentRememberMeToken persistentToken = repository.getTokenForSeries(token.getSeries());
        RememberMeToken found = new RememberMeToken(persistentToken);
        assertEquals(token, found);
    }

    @Test
    public void removeUserTokens(){
        RememberMeToken token = getTestToken();
        repository.save(token);
        repository.removeUserTokens(token.getUsername());
        RememberMeToken found = repository.findOne(token.getSeries());
        assertNull(found);
    }

    @Test
    public void shouldReturnNull(){
        PersistentRememberMeToken nullToken = repository.getTokenForSeries("null");
        assertNull(nullToken);
    }

    private RememberMeToken getTestToken(){
        return new RememberMeToken("user", "test series", "test token value", new Date(System.nanoTime()));
    }


}
