package com.cbank.services.impl.message;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.aspectj.bridge.Version.text;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final JavaMailSender mailSender;
    private final MessageTextTemplateFactory templateFactory;
    private final FeedbackValidator feedbackValidator;
    private final FeedbackRepository feedbackRepository;
    private final MessageRepository messageRepository;


    @Value("${spring.mail.username}")
    private String from;

    @Override
    public Message send(String recipient, MessageTemplate template, Map<String, Object> context) {
        log.debug("#send({},{},{})", recipient, template, context);

        val message = Message.builder()
                .to(recipient)
                .title(template.getTitle())
                .body(templateFactory.create(template, context))
                .build();

        mailSender.send(
                save(message).toMailMessage(from)
        );

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
