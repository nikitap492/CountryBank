package com.bank.service.impl;

import com.bank.domain.other_services.Message;
import com.bank.repositories.MessageRepository;
import com.bank.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link MessageService}
 */
@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;
    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void save(Message message) {
        messageRepository.save(message);
        log.debug("Message : " + message + " has been saved");
    }

    @Override
    public void send(String to, String subject, String text) {
        log.debug("Sending message to " + to);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setText(text);
        mailMessage.setSubject(subject);
        mailSender.send(mailMessage);
    }
}
