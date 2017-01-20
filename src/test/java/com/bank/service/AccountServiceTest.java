package com.bank.service;

import com.bank.Application;
import com.bank.domain.Account;
import com.bank.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.bank.DataTest.*;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class AccountServiceTest {


    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Test
    public void shouldSaveAccount() throws Exception {
        final long before = accountService.count();
        String daniel = "Daniel Black";
        User danielUser = new User(daniel, daniel, daniel + "@test.ru");
        userService.save(danielUser);
        Account danielAcc = new Account(danielUser, daniel, daniel + "_home");
        accountService.save(danielAcc);
        final long after = accountService.count();
        assertEquals(before + 1, after);
    }

    @Test
    public void shouldGetAllAccountsAndCountThem() throws Exception {
        final List<Account> accounts = accountService.getAll();
        assertNotNull(accounts);
        final int size = accounts.size();
        assertTrue(size > 0);
        assertEquals(size, accountService.count());
    }

    @Test
    public void shouldFindAccountById() {
        final Account accountById = accountService.findById(susieAcc.getId());
        assertEquals(susieAcc, accountById);
    }

    @Test
    public void shouldFindAccountByName() throws Exception {
        final Account accountByName = accountService.findByName(susieAcc.getName());
        assertEquals(susieAcc, accountByName);
    }

    @Test
    public void shouldFindAccountByUsername() {
        Account accountByUser = accountService.findByUsername(bartUser.getUsername());
        assertEquals(bartAcc, accountByUser);
    }

}
