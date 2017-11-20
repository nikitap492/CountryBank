package com.cbank.services;

import com.cbank.domain.security.*;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface TokenService {



    Optional<BaseToken> byId(String id);

    BaseToken create(String username, BaseTokenType tokenType);

    BaseToken invalidate(BaseToken token);

    //todo to task
    int checkForExpired();

}
