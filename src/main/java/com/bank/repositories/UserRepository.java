package com.bank.repositories;

import com.bank.domain.user.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String> {
    User findByEmail(String email);
}
