package com.cbank.services;

import com.cbank.domain.Account;
import com.cbank.domain.RegistrationForm;
import com.cbank.domain.user.UserProjection;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface RegistrationService {

    Account register(RegistrationForm form);

    Account confirm();

}
