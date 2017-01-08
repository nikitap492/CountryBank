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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Poshivalov Nikita
 * @since 08.12.2016.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class MessageValidatorTest {


    @Autowired
    @Spy
    private MessageValidator messageValidator;

    @Test
    public void messageNameValidatorTest() {
        assertFalse(messageValidator.validateName("dd3232"));
        assertFalse(messageValidator.validateName("Sddfd#c"));
        assertFalse(messageValidator.validateName("I"));
        assertTrue(messageValidator.validateName("Test"));
    }

    @Test
    public void messageValidatorTest() {
        String name = "tester";
        String email = "test@email.test";
        Message message = new Message(email, name, "");
        String validated = messageValidator.validate(message);
        assertTrue(validated != null);
        verify(messageValidator, times(1)).validateName(name);
    }
}
