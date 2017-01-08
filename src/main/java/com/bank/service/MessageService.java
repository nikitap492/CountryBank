package com.bank.service;

import com.bank.domain.other_services.Message;

/**
 * {@link com.bank.service.impl.MessageServiceImpl}
 */
public interface MessageService {

    void save(Message message);

    void send(String to, String subject, String text);

}
