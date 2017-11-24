package com.cbank.services;

import com.cbank.domain.Account;
import com.cbank.domain.RegistrationForm;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface RegistrationService {

    Account register(RegistrationForm form);

    void confirm(String token);

    boolean check(String usernameOrEmail);
}
