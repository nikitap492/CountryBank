package com.cbank.services.impl.message

import com.cbank.config.MailProperties
import com.cbank.domain.message.Feedback
import com.cbank.domain.message.Message
import com.cbank.domain.message.MessageTemplate
import com.cbank.repositories.FeedbackRepository
import com.cbank.repositories.MessageRepository
import com.cbank.validators.FeedbackValidator
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 25.12.2017.
 */
class MessageServiceImplUnitTest extends Specification {

    def mailSender = Mock(JavaMailSender)
    def messageTemplateFactory = Mock(MessageTemplateFactory)
    def feedbackValidator = Mock(FeedbackValidator)
    def feedbackRepository = Mock(FeedbackRepository)
    def messageRepository = Mock(MessageRepository)
    def mailProperties = new MailProperties()
    def messageService = new MessageServiceImpl(mailSender, messageTemplateFactory, feedbackValidator,
            feedbackRepository, messageRepository, mailProperties)

    def "send"() {
        given:
        messageTemplateFactory.create(*_) >> "template"
        mailProperties.enable = true
        when: messageService.send("recipient", MessageTemplate.ACCESS_RECOVERY, [:])
        then:
        1 * messageRepository.save(_)
        1 * mailSender.send(*_)
    }

    def "persist"() {
        given: def feedback = new Feedback("name", "email", "body")
        when: messageService.persist(feedback)
        then: 1 * feedbackRepository.save(feedback)
    }

    def "save"() {
        given: def message = new Message("to", "title", "body")
        when: messageService.save(message)
        then: 1 * messageRepository.save(message)
    }

}
