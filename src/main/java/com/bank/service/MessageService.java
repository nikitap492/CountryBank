package com.bank.service;

import com.bank.domain.other_services.Message;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * {@link com.bank.service.impl.MessageServiceImpl}
 */
public interface MessageService {

    void save(Message message);

    void send(String to, String subject, String text);

    void setMailSender(JavaMailSender sender);

}
