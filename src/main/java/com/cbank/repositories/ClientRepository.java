package com.cbank.repositories;

import com.cbank.domain.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findByUserId(String userId);

    Optional<Client> findByEmail(String email);
}
