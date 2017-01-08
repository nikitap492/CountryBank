package com.bank.service;

import com.bank.Application;
import com.bank.DataTest;
import com.bank.domain.other_services.Message;
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


public class MessageServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageService messageService;

    @Test
    public void shouldSaveMessage() {
        Message message = new Message(DataTest.bartAcc, "Hello, Country Bank!");
        messageService.save(message);
    }


}
