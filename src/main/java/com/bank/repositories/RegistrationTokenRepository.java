package com.bank.repositories;

import com.bank.domain.other.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDateTime;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, String> {

    @Modifying
    @Query("UPDATE RegistrationToken t SET t.validity = false WHERE t.tokenDateTime <= :date ")
    int checkNonValidity(@Param("date") LocalDateTime date);

}
