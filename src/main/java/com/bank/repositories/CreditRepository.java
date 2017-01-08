package com.bank.repositories;

import com.bank.domain.Account;
import com.bank.domain.services.credit.Credit;
import com.bank.domain.services.credit.CreditState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findByState(CreditState state);

    /**
     * @return sum for credits by {@param account}
     */
    @Query("SELECT sum(c.money) FROM Credit c JOIN c.bill AS b WHERE b.account = :account")
    Double sumAllCreditsByAccount(@Param("account") Account account);

}
