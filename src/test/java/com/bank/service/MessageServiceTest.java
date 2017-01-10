package com.bank.service;

import com.bank.Application;
import com.bank.DataTest;
import com.bank.domain.other_services.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Value("${spring.mail.username}")
    private String from;
    private SimpleMailMessage mailMessage;
    private String test = "test";
    private JavaMailSender spy;

    @PostConstruct
    public void init(){
        spy = spy(JavaMailSender.class);
        this.mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(test);
        mailMessage.setText(test);
        mailMessage.setSubject(test);
        messageService.setMailSender(spy);
    }

    @Test
    public void shouldSaveMessage() {
        Message message = new Message(DataTest.bartAcc, "Hello, Country Bank!");
        messageService.save(message);
    }

    @Test
    public void shouldSendMessage(){
        messageService.send(test, test, test);
        verify(spy, times(1)).send(mailMessage);
    }


}
