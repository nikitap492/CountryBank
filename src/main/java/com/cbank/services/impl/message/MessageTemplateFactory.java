package com.cbank.services.impl.message;

import com.cbank.domain.message.MessageTemplate;

import java.util.Map;

/**
 * @author Podshivalov N.A.
 * @since 25.12.2017.
 */
public interface MessageTemplateFactory {

    String create(MessageTemplate template, Map<String, Object> context);
}
