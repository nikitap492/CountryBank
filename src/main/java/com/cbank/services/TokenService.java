package com.cbank.services;

import com.cbank.domain.security.BaseToken;
import com.cbank.domain.security.BaseTokenType;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface TokenService {

    BaseToken create(String username, BaseTokenType tokenType);

    //todo to task
    int checkForExpired();

    BaseToken get(String uuid);
}
