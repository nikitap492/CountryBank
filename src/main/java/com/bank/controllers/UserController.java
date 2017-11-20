package com.bank.controllers;

import com.bank.domain.other.RegistrationToken;
import com.bank.domain.other.ResetPasswordToken;
import com.bank.domain.user.UserRegister;
import com.bank.service.MessageService;
import com.bank.service.UserService;
import com.bank.validators.TokenValidator;
import com.bank.validators.UserValidator;
import com.bank.validators.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Poshivalov Nikita
 * @since 16.11.2016.
 */

@Slf4j
@RestController
@PreAuthorize("isAnonymous()")
@AllArgsConstructor
public class UserController {
    private final UserService service;
    private final UserValidator validator;
    private final MessageService messageService;
    private final TokenValidator tokenValidator;

    /**
     * Creating new user
     *
     * @return {@link ResponseEntity<String>} status and message
     */
    @RequestMapping(value = "/registration", method = POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> addNewUser(@RequestBody UserRegister us) {
        log.debug("Try to save new user : " + us.getUser().getUsername());
        String error = validator.validate(us);
        if (error != null) {
            log.debug(error + " for user : " + us);
            return ResponseEntity.badRequest().body(error);
        }
        service.save(us);
        log.debug("User was saved successfully" + ": " + us);
        RegistrationToken token = service.createRegistrationToken(us.getUser());
        messageService.send(token.getUser().getEmail(), "Confirm your registration", getRegistrationText(token));
        return ResponseEntity.ok().body(null);
    }

    private String getRegistrationText(RegistrationToken token) {
        return "Country bank is greeting to you\nPlease, click the link http://localhost:8000/confirm?token=" + token.getToken() + " for confirm your registration";
    }

    @RequestMapping(value = "/api/user/check", method = GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Boolean checkUsernameOrEmailAlreadyExist(@RequestParam("val") String usernameOrEmail) {
        return service.findByUsernameOrEmail(usernameOrEmail);
    }

    @RequestMapping(value = "/reset_password", method = POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> resetProcess(@RequestParam("token") String token,  @RequestBody UserRegister us) {
        String password = us.getUser().getPassword();
        ValidationResult<ResetPasswordToken> result = tokenValidator.validateResetPasswordToken(token);
        if (result.hasError()) {
            return ResponseEntity.badRequest().body(result.getError());
        }
        if (password.length() < 4) {
            return ResponseEntity.badRequest().body("Password is too short");
        }
        service.resetPassword(result.getEntity(), password);
        return ResponseEntity.ok().body("Password has changed for user " + result.getEntity().getUser().getUsername());
    }

    @RequestMapping(value = "/api/user/forget", method = POST)
    public ResponseEntity<String> forget(@RequestParam("loginOrEmail") String loginOrEmail) {
        log.debug("Trying to find " + loginOrEmail);
        if (!service.findByUsernameOrEmail(loginOrEmail)) {
            log.debug(loginOrEmail + " user do not exist");
            return ResponseEntity.badRequest().body("Login and email are not exist");
        }
        ResetPasswordToken token = service.createResetPasswordToken(loginOrEmail);
        messageService.send(token.getUser().getEmail(), "Reset Password", getResetPasswordText(token));
        return ResponseEntity.ok().body("Message was sent on your email.");
    }

    private String getResetPasswordText(ResetPasswordToken token) {
        return "Country bank is greeting to you\nPlease, click the link http://localhost:8000/reset_password?token=" + token.getToken() + " for reset password";
    }

    @RequestMapping(value = "/confirm", method = POST)
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        ValidationResult<RegistrationToken> result = tokenValidator.validateRegistrationToken(token);
        if (result.hasError()) {
            log.debug("Registration confirm error " + result.getError());
            return ResponseEntity.badRequest().body(result.getError());
        }
        service.setEnabled(result.getEntity());
        log.debug("Successful confirmation");
        return ResponseEntity.ok().body("Your registration was successfully confirmed");
    }


}
