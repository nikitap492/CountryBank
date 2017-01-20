package com.bank.validators;

import com.bank.domain.other_services.Message;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.bank.validators.Validator.validateEmail;
import static org.springframework.util.StringUtils.hasLength;

@Component
public class MessageValidator {

    private static final String NAME_REGEX = "^[_A-Za-z]{2,}";
    private static final Pattern pattern = Pattern.compile(NAME_REGEX);
    static final String EMPTY_MESSAGE = "Message is empty";
    static final String EMPTY_NAME = "You should enter your name";
    static final String INCORRECT_EMAIL = "Incorrect email";

    public String validate(Message message) {
        if (!validateEmail(message.getEmail())) {
            return INCORRECT_EMAIL;
        }
        if (!validateName(message.getName())) {
            return EMPTY_NAME;
        }
        if (!hasLength(message.getMessage())) {
            return EMPTY_MESSAGE;
        }
        return null;
    }

    //Name might has whitespaces
    boolean validateName(String name) {
        return pattern.matcher(name.replace(" ", "")).matches();
    }


}
