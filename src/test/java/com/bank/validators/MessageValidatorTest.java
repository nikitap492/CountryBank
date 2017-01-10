package com.bank.validators;

import com.bank.Application;
import com.bank.domain.other_services.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class MessageValidatorTest {


    @Autowired @Spy
    private MessageValidator messageValidator;

    private static final String NAME = "tester";
    private static final String EMAIL = "test@EMAIL.test";
    private static final String MESSAGE_TEXT = "test message";

    @Test
    public void messageNameValidatorTest() {
        assertFalse(messageValidator.validateName("dd3232"));
        assertFalse(messageValidator.validateName("Sddfd#c"));
        assertFalse(messageValidator.validateName("I"));
        assertTrue(messageValidator.validateName("Test"));
    }

    @Test
    public void messageValidatorWithEmptyMessageTest() {
        Message message = new Message(EMAIL, NAME, "");
        String validated = messageValidator.validate(message);
        assertEquals(MessageValidator.EMPTY_MESSAGE, validated);
        verify(messageValidator, times(1)).validateName(NAME);
    }

    @Test
    public void messageValidatorWithIncorrectEmailTest() {
        Message message = new Message("incorrect@email", NAME, MESSAGE_TEXT);
        String validated = messageValidator.validate(message);
        assertEquals(MessageValidator.INCORRECT_EMAIL, validated);
    }

    @Test
    public void messageValidatorEmptyNameTest() {
        Message message = new Message(EMAIL, "", MESSAGE_TEXT);
        String validated = messageValidator.validate(message);
        assertEquals(MessageValidator.EMPTY_NAME, validated);
        verify(messageValidator, times(1)).validateName("");
    }

    @Test
    public void messageTest() {
        Message message = new Message(EMAIL, NAME, MESSAGE_TEXT);
        String validated = messageValidator.validate(message);
        assertNull(validated);
    }
}
