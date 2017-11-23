package com.cbank.repositories;

import com.cbank.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}
