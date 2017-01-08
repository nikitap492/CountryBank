package com.bank.validators;


import com.bank.domain.other.BaseToken;
import com.bank.domain.other.RegistrationToken;
import com.bank.domain.other.ResetPasswordToken;
import com.bank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TokenValidator {

    private static final Logger log = LoggerFactory.getLogger(TokenValidator.class);

    @Autowired
    private UserService service;

    public ValidationResult<ResetPasswordToken> validateResetPasswordToken(String uuid) {
        ValidationResult<ResetPasswordToken> result = new ValidationResult<>();
        try {
            result = validateToken(service.findResetPasswordToken((uuid)));
        } catch (NullPointerException e) {
            result.setError("Token was not found");
            log.debug("Token " + uuid + " was not found");
        }
        return result;
    }

    public ValidationResult<RegistrationToken> validateRegistrationToken(String uuid) {
        ValidationResult<RegistrationToken> result = new ValidationResult<>();
        try {
            result = validateToken(service.findRegistrationToken(uuid));
        } catch (NullPointerException e) {
            result.setError("Token was not found");
            log.debug("Token " + uuid + " was not found");
        }
        return result;

    }


    public <T extends BaseToken> ValidationResult<T> validateToken(T token) {
        ValidationResult<T> result = new ValidationResult<>();

        if (!token.getValidity()) {
            result.setError("Token " + token.getToken() + " is not validity");
            return result;
        }
        LocalDateTime now = LocalDateTime.now();
        int dayOfYear = token.getTokenDateTime().getDayOfYear();
        if (now.getDayOfYear() - dayOfYear == 0) {
            result.setEntity(token);
        } else if (now.getHour() - token.getTokenDateTime().getHour() < 0 && now.getDayOfYear() - dayOfYear == 1) {
            result.setEntity(token);
        } else {
            service.setNonValidityToken(token);
            result.setError("Token is not validity");
            log.debug("Token " + token.getToken() + " is not validity anymore");
            return result;
        }
        return result;
    }


}
