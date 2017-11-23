package com.cbank.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Podshivalov N.A.
 * @since 23.11.2017.
 */
public class AuthenticationProcessException extends AuthenticationException {

    public AuthenticationProcessException(String msg) {
        super(msg);
    }
}
