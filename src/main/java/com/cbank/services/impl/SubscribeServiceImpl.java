package com.cbank.services.impl;


import com.cbank.domain.Subscriber;
import com.cbank.repositories.SubscriberRepository;
import com.cbank.services.SubscribeService;
import com.cbank.validators.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Service
@AllArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {
    private final SubscriberRepository subscriberRepository;

    @Override
    public Subscriber subscribe(Subscriber subscriber) {
        ValidationUtils.email(subscriber.getEmail());
        return subscriberRepository.findByEmail(subscriber.getEmail())
                .orElseGet(() -> subscriberRepository.save(subscriber));
    }

    @Override
    public Optional<Subscriber> byEmail(String email) {
        return subscriberRepository.findByEmail(email);
    }

    @Override
    public void unsubscribe(String email) {
        subscriberRepository.deleteByEmail(email);
    }
}
