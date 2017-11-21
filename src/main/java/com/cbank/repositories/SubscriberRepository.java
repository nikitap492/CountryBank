package com.cbank.repositories;

import com.cbank.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Optional<Subscriber> findByEmail(String email);

}
