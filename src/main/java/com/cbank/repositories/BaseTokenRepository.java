package com.cbank.repositories;

import com.cbank.domain.security.BaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface BaseTokenRepository extends JpaRepository<BaseToken, String> {

    @Modifying
    @Transactional
    @Query("UPDATE BaseToken t SET t.valid = false WHERE t.createdAt <= ?1 ")
    int expire(LocalDateTime date);
}
