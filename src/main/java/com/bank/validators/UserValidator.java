package com.bank.validators;

import com.bank.domain.user.User;
import com.bank.domain.user.UserRegister;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bank.validators.Validator.*;
import static org.springframework.util.StringUtils.hasLength;


@Component
public class UserValidator {

    /**
     * Pattern and regex for username
     */
    private static final String USERNAME_REGEX = "^(?=.*[a-z])[a-z0-9]{3,}";
    private static final Pattern pattern = Pattern.compile(USERNAME_REGEX);

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
            return "You have not entered username or email";
        if (!validatePassword(user.getPassword())) {
            return "Your password has small length";
        }
        if (!validateUsername(username)) {
            return "Incorrect username";
        }
        if (!isUserNotExist(username)) {
            return "Username already had been registered";
        }
        if (!validateEmail(email)) {
            return "Incorrect email";
        }
        if (!isUserNotExist(email)) {
            return "Email already had been registered";
        }
        if (!validateAccount(userRegister.getAccount())) {
            return "Address or Name has small length";
        }
        return null;
    }

    public boolean validatePassword(String password) {
        return hasLength(password) && password.length() >= 5;
    }

    public boolean validateUsername(String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Checking whether user is exist
     * method looking for user by email or username
     *
     * @param usernameOrEmail
     */
    public boolean isUserNotExist(String usernameOrEmail) {
        UserDetails byUsername = userService.findByUsername(usernameOrEmail);
        return byUsername == null;
    }


}
