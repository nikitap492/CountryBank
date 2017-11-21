package com.cbank.exceptions;

import lombok.Getter;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Getter
public class ValidationException extends RuntimeException{
    private String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }
}
