package com.cbank.repositories;

import com.cbank.domain.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Podshivalov N.A.
 * @since 20.12.2017.
 */
public interface CreditRepository extends JpaRepository<Credit, Long> {
}
