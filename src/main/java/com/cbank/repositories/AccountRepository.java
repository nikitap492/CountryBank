package com.cbank.repositories;

import com.cbank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface AccountRepository  extends JpaRepository<Account, Long>{

    Optional<Account> findByClientIdAndCurrentIsTrue(Long clientId);

    Optional<Account> findByNum(String accountNum);

    Collection<Account> findAllByClientId(Long clientId);
}
