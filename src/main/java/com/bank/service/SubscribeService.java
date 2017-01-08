package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.other_services.Subscriber;

/**
 * {@link com.bank.service.impl.SubscribeServiceImpl}
 */
public interface SubscribeService {


    void save(Subscriber subscriber);

    boolean emailIsSubscribed(String email);

    boolean accountIsSubscribed(Account account);

    void deleteByAccount(Account account);

    long count();
}
