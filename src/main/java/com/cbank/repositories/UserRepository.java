package com.cbank.repositories;

import com.cbank.domain.user.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String> {
}
