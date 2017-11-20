package com.bank.validators;


import com.bank.domain.other.BaseToken;
import com.bank.domain.other.RegistrationToken;
import com.bank.domain.other.ResetPasswordToken;
import com.bank.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class TokenValidator {

    static final String NOT_VALIDITY = "Token is not validity";
    static final String NOT_FOUND = "Token was not found";
    private static Function<String, ResetPasswordToken> RESET_PASSWORD_TOKEN;
    private static Function<String, RegistrationToken> REGISTRATION_TOKEN;

    private final UserService service;

    @PostConstruct
    public void init() {
        REGISTRATION_TOKEN = service::findRegistrationToken;
        RESET_PASSWORD_TOKEN = service::findResetPasswordToken;
    }

    public ValidationResult<ResetPasswordToken> validateResetPasswordToken(String uuid) {
        return validateToken(RESET_PASSWORD_TOKEN, uuid);
    }

    public ValidationResult<RegistrationToken> validateRegistrationToken(String uuid) {
        return validateToken(REGISTRATION_TOKEN, uuid);
    }


    private <T extends BaseToken> ValidationResult<T> validateToken(Function<String, T> function, String uuid) {
        ValidationResult<T> result = new ValidationResult<>();
        try {
            T token = function.apply(uuid);
            if (!token.getValidity()) {
                return setNonValidityResult(result, token);
            }
            LocalDateTime now = LocalDateTime.now();
            int dayOfYear = token.getTokenDateTime().getDayOfYear();
            if (now.getDayOfYear() - dayOfYear == 0) {
                result.setEntity(token);
            } else if (now.getHour() - token.getTokenDateTime().getHour() < 0 && now.getDayOfYear() - dayOfYear == 1) {
                result.setEntity(token);
            } else {
                service.setNonValidityToken(token);
                return setNonValidityResult(result, token);
            }
        } catch (NullPointerException e) {
            result.setError(NOT_FOUND);
            log.debug("Token " + uuid + " was not found");
        }
        return result;
    }

    private <T extends BaseToken> ValidationResult<T> setNonValidityResult(ValidationResult<T> result, T token){
        result.setError(NOT_VALIDITY);
        log.debug("Token " + token.getToken() + " is not validity anymore");
        return result;
    }



}
