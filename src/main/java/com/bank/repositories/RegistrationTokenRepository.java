package com.bank.repositories;

import com.bank.domain.other.RegistrationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, String> {

    @Modifying
    @Query("UPDATE ResetPasswordToken t SET t.validity = false WHERE t.tokenDateTime - CURRENT_DATE >= 2 ")
    int checkNonValidity();

}
