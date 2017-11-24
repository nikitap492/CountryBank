package com.cbank.services;

import com.cbank.domain.message.Feedback;
import com.cbank.domain.message.Message;
import com.cbank.domain.message.MessageTemplate;

import java.util.Map;


public interface MessageService extends PersistableService<Message> {

    Message send(String recipient, MessageTemplate template, Map<String, Object> context);

    Feedback persist(Feedback feedback);

}
