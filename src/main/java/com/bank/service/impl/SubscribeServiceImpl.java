package com.bank.service.impl;

import com.bank.domain.Account;
import com.bank.domain.other_services.Subscriber;
import com.bank.repositories.SubscriberRepository;
import com.bank.service.SubscribeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link SubscribeService}
 */
@Service
public class SubscribeServiceImpl implements SubscribeService {

    private static final Logger log = LoggerFactory.getLogger(SubscribeServiceImpl.class);

    @Autowired
    private SubscriberRepository repository;

    @Override
    public void save(Subscriber subscriber) {
        repository.save(subscriber);
        log.debug("Subscriber : " + subscriber + " has been saved");
    }

    /**
     * Attempt to find subscriber by {@param email}
     */
    @Override
    public boolean emailIsSubscribed(String email) {
        return repository.findOne(email) != null;
    }

    /**
     * Checking what {@param account} is subscriber
     */
    @Override
    public boolean accountIsSubscribed(Account account) {
        return emailIsSubscribed(account.getUser().getEmail());
    }

    @Override
    public void deleteByAccount(Account account) {
        repository.delete(new Subscriber(account));
    }

    @Override
    public long count() {
        return repository.count();
    }

}
