package com.cbank.repositories;

import com.cbank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
}
