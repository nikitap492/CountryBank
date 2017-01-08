package com.bank.repositories;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findBillsByAccount(Account account);

    Bill findByUuid(UUID uuid);

    /**
     * Method set {@code isCurrent = false} for
     *
     * @param account 's bill
     * @return numbers of updated bills
     */

    @Modifying
    @Transactional
    @Query("UPDATE Bill b SET b.isCurrent = false WHERE b.account = :account")
    int cleanCurrentStatusByAccount(@Param("account") Account account);

    /**
     * Method finds current bill for user with username =
     *
     * @param username
     */
    @Query("SELECT b FROM Bill b  " +
            "INNER JOIN b.account AS a " +
            "INNER JOIN a.user AS u " +
            "WHERE u.username = :name AND b.isCurrent = true")
    Bill findCurrentBillByUsername(@Param("name") String username);

    /**
     * Counting account's bill
     */
    @Query("SELECT count(b) FROM Bill b WHERE b.account = :account")
    int countBillsByAccount(@Param("account") Account account);

    /**
     * Method finds current bill for
     *
     * @param account
     */
    @Query("SELECT b FROM Bill b WHERE b.account  = :account AND b.isCurrent = true")
    Bill getCurrentForAccount(@Param("account") Account account);
}
