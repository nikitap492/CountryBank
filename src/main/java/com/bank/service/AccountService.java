package com.bank.service;

import com.bank.domain.Account;

import java.util.List;

/**
 * {@link com.bank.service.impl.AccountServiceImpl}
 */
public interface AccountService {

    void save(Account... account);

    List<Account> getAll();

    Account findByName(String name);

    Account findById(Long id);

    Account findByUsername(String username);

    long count();
}
