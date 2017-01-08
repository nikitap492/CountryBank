package com.bank.repositories;

import com.bank.domain.Account;
import com.bank.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByName(String name);

    Account findByUser(User user);
}
