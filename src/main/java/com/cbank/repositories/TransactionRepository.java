package com.cbank.repositories;

import com.cbank.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    @Query("select t from Transaction t where t.payer = ?1 or t.recipient = ?1")
    Collection<Transaction> findAllByAccountNum(String accountNum);
}
