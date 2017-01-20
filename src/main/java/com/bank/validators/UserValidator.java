package com.bank.validators;

import com.bank.domain.user.User;
import com.bank.domain.user.UserRegister;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bank.validators.Validator.validateAccount;
import static com.bank.validators.Validator.validateEmail;
import static org.springframework.util.StringUtils.hasLength;


@Component
public class UserValidator {

    /**
     * Pattern and regex for username
     */
    private static final String USERNAME_REGEX = "^(?=.*[a-z])[a-z0-9]{3,}";
    private static final Pattern pattern = Pattern.compile(USERNAME_REGEX);

    static final String NULL = "You have not entered username or email";
    static final String SMALL_PASSWORD = "Your password has small length";
    static final String INCORRECT_USERNAME = "Incorrect username";
    static final String INCORRECT_EMAIL = "Incorrect email";
    static final String USERNAME_ALREADY_EXIST = "Username already had been registered";
    static final String EMAIL_ALREADY_EXIST = "Email already had been registered";
    static final String SMALL_ADDRESS_OR_NAME = "Address or Name has small length";

    @Autowired
    private UserService userService;

    /**
     * @param userRegister is special entity for ajax
     * @return {@link String} of error
     * Email is checked by {@code Validator.validateEmail}
     */
    public String validate(UserRegister userRegister) {
        User user = userRegister.getUser();
        String username = user.getUsername();
        String email = user.getEmail();
        if (username == null || email == null)
            return NULL;
        if (!validatePassword(user.getPassword()))  return SMALL_PASSWORD;
        if (!validateUsername(username)) return INCORRECT_USERNAME;
        if (!isUserNotExist(username)) return USERNAME_ALREADY_EXIST;
        if (!validateEmail(email)) return INCORRECT_EMAIL;
        if (!isUserNotExist(email))  return EMAIL_ALREADY_EXIST;
        if (!validateAccount(userRegister.getAccount()))  return SMALL_ADDRESS_OR_NAME;
        return null;
    }

    boolean validatePassword(String password) {
        return hasLength(password) && password.length() >= 5;
    }

    boolean validateUsername(String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Checking whether user is exist
     * method looking for user by email or username
     *
     * @param usernameOrEmail is user identifier
     */
    boolean isUserNotExist(String usernameOrEmail) {
        return !userService.findByUsernameOrEmail(usernameOrEmail);
    }


}
