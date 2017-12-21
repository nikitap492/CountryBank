package com.cbank.repositories;

import com.cbank.domain.credit.Credit;
import com.cbank.domain.credit.CreditType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

/**
 * @author Podshivalov N.A.
 * @since 20.12.2017.
 */
public interface CreditRepository extends JpaRepository<Credit, Long> {

    Collection<Credit> findAllByAccountIdAndTypeIn(Long accountId, Set<CreditType> creditType);
}
