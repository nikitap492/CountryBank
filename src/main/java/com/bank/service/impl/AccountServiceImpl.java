package com.bank.service.impl;

import com.bank.domain.Account;
import com.bank.repositories.AccountRepository;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation on {@link AccountService}
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService service;

    /**
     * Method for saving
     *
     * @param accounts
     */
    @Override
    public void save(Account... accounts) {
        for (Account account : accounts) {
            accountRepository.save(account);
            log.debug("Account : " + account + " has been saved");
        }
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findByName(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUser(service.findByUsername(username));
    }

    /**
     * return number of all accounts
     */
    @Override
    public long count() {
        return accountRepository.count();
    }


}
