package com.cbank.services;

import com.cbank.domain.Subscriber;

import java.util.Optional;

/**
 * {@link com.bank.service.impl.SubscribeServiceImpl}
 */
public interface SubscribeService {

    Subscriber subscribe(Subscriber subscriber);

    Optional<Subscriber> byEmail(String email);

    void unsubscribe(String email) ;
}
