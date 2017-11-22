package com.cbank.validators;

import com.cbank.domain.message.Feedback;
import com.cbank.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasLength;

@Component
public class FeedbackValidator implements Validator<Feedback> {

    private static final String EMPTY_BODY = "Message is empty";

    @Override
    public void validate(Feedback feedback) {
        ValidationUtils.email(feedback.getEmail());
        ValidationUtils.name(feedback.getName());
        if (!hasLength(feedback.getBody())) throw new ValidationException(EMPTY_BODY);
    }

}
