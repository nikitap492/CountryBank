package com.bank.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({AccountServiceTest.class, BillServiceTest.class,
        CreditService.class, MessageServiceTest.class,
        MovementServiceTest.class, SubscribeServiceTest.class,
        UserServiceTest.class})
public class ServiceSuiteTest {

}