package com.bank.repositories;


import com.bank.domain.other.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, String> {

    @Modifying
    @Query("UPDATE ResetPasswordToken t SET t.validity = false WHERE t.tokenDateTime <= :date ")
    int checkNonValidity(@Param("date") LocalDateTime date);

}
