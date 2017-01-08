package com.bank.validators;

import com.bank.domain.other_services.Message;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.bank.validators.Validator.*;
import static org.springframework.util.StringUtils.*;

@Component
public class MessageValidator {

    private static final String NAME_REGEX = "^[_A-Za-z]{2,}";
    private static final Pattern pattern = Pattern.compile(NAME_REGEX);

    public String validate(Message message) {
        if (!validateEmail(message.getEmail())) {
            return "Incorrect email";
        }
        if (!validateName(message.getName())) {
            return "You should enter your name";
        }
        if (!hasLength(message.getMessage())) {
            return "Message is empty";
        }
        return null;
    }

    //Name might has whitespaces
    public boolean validateName(String name) {
        return pattern.matcher(name.replace(" ", "")).matches();
    }


}
