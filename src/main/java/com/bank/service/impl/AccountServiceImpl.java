package com.bank.service.impl;

import com.bank.domain.Account;
import com.bank.repositories.AccountRepository;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation on {@link AccountService}
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService service;

    /**
     * Method for saving
     *
     * @param accounts
     */
    @Override
    @Transactional
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
