package com.bank.repositories;


import com.bank.domain.other.ResetPasswordToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, String> {

    @Modifying
    @Query("UPDATE ResetPasswordToken t SET t.validity = false WHERE t.tokenDateTime - CURRENT_DATE >= 2 ")
    int checkNonValidity();

}
