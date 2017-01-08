package com.bank.validators;

import com.bank.domain.Account;

import java.util.regex.Pattern;

/**
 * Contains common validation methods
 */

public final class Validator {

    private static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    public static boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static boolean validateAccount(Account account) {
        return account.getName().length() > 3 && account.getAddress().length() > 5;
    }

    public static ValidationResult<Double> validateDouble(String d, String description) {
        ValidationResult<Double> result = new ValidationResult<>();
        try {
            Double parseDouble = Double.parseDouble(d);
            result.setEntity(parseDouble);
        } catch (IllegalArgumentException e) {
            result.setError("Incorrect " + description + " input");
        }
        return result;
    }
}
