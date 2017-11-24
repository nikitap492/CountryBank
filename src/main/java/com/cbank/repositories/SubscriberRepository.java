package com.cbank.repositories;

import com.cbank.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Optional<Subscriber> findByEmail(String email);

    @Transactional
    @Modifying
    void deleteByEmail(String email);

}
