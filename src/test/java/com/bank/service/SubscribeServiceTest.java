package com.bank.service;

import com.bank.Application;
import com.bank.domain.other_services.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bank.DataTest.*;
import static org.junit.Assert.*;


public class SubscribeServiceTest {

    private static final Logger log = LoggerFactory.getLogger(SubscribeServiceTest.class);

    @Autowired
    private SubscribeService subscribeService;

    @Test
    public void shouldSaveSubscriberAndFindByEmail() {
        String email = "test@test.test";
        Subscriber subscriber = new Subscriber(email);
        subscribeService.save(subscriber);
        subscribeService.emailIsSubscribed(email);
    }

    @Test
    public void shouldSaveSubscriberAndFindByAccount() {
        Subscriber subscriber = new Subscriber(bartAcc);
        subscribeService.save(subscriber);
        subscribeService.accountIsSubscribed(bartAcc);
    }

    @Test
    public void shouldCountSubscribersCorrectly() {
        long before = subscribeService.count();
        subscribeService.save(new Subscriber(susieAcc));
        long after = subscribeService.count();
        assertEquals(before + 1, after);
    }

    @Test
    public void shouldDeleteSubscriber() {
        subscribeService.save(new Subscriber(jimmyAcc));
        long before = subscribeService.count();
        subscribeService.deleteByAccount(jimmyAcc);
        long after = subscribeService.count();
        assertEquals(before - 1, after);
        assertFalse(subscribeService.accountIsSubscribed(jimmyAcc));
    }

}
