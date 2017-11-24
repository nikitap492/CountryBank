package com.cbank.services;

import com.cbank.domain.Client;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface ClientService extends PersistableService<Client>{

    Optional<Client> byUserId(String username);

    void accessRecovery(String loginOrEmail);
}
