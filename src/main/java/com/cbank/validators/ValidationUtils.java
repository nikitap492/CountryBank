package com.cbank.validators;


import com.cbank.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public class ValidationUtils {

    private static final String EMAIL_REGEX = "[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    private static final String NAME_REGEX = "^[a-zA-Z_ ]{5,}";
    private static final Pattern namePattern = Pattern.compile(NAME_REGEX);

    private static final String ACCOUNT_REGEX = "^[0-9]{16}$";
    private static final Pattern accountPattern = Pattern.compile(ACCOUNT_REGEX);

    public static void email(String email) {
        if (email == null || !emailPattern.matcher(email).matches()) throw new ValidationException("The email is incorrect");
    }

    public static void name(String name) {
        if (name == null ||!namePattern.matcher(name).matches()) throw new ValidationException("The name is incorrect or too short");
    }

    public static void account(String account) {
        if (account == null || !accountPattern.matcher(account).matches())
            throw new ValidationException(String.format("The account %s is incorrect", account));
    }

    public static void zeroAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Amount should be great than 0");
    }


}
