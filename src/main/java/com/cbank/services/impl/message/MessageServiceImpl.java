package com.cbank.services.impl.message;

import com.cbank.config.MailProperties;
import com.cbank.domain.message.Feedback;
import com.cbank.domain.message.Message;
import com.cbank.domain.message.MessageTemplate;
import com.cbank.repositories.FeedbackRepository;
import com.cbank.repositories.MessageRepository;
import com.cbank.services.MessageService;
import com.cbank.validators.FeedbackValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final JavaMailSender mailSender;
    private final MessageTemplateFactory templateFactory;
    private final FeedbackValidator feedbackValidator;
    private final FeedbackRepository feedbackRepository;
    private final MessageRepository messageRepository;
    private final MailProperties mailProperties;


    @Override
    public Message send(String recipient, MessageTemplate template, Map<String, Object> context) {
        log.debug("#send({},{},{})", recipient, template, context);
        val message = new Message(recipient, template.getTitle(), templateFactory.create(template, context));
        save(message);

        if (mailProperties.getEnable()){
            mailSender.send(message.toMailMessage(mailProperties.getUsername()));
        }
        return message;
    }

    @Override
    public Feedback persist(Feedback feedback) {
        feedbackValidator.validate(feedback);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
