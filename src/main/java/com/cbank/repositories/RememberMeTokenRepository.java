package com.cbank.repositories;

import com.cbank.domain.security.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, String>, PersistentTokenRepository {


    @Override
    default void createNewToken(PersistentRememberMeToken token) {
        save(new RememberMeToken(token));
    }


    @Override
    @Modifying
    @Transactional
    @Query("UPDATE RememberMeToken t SET t.tokenValue = ?2, t.date= ?3 WHERE t.series = ?1")
    void updateToken(String series, String tokenValue, Date lastUsed);


    @Override
    default PersistentRememberMeToken getTokenForSeries(String series) {
        return Optional.ofNullable(findOne(series))
                .map(RememberMeToken::getPersistentToken)
                .orElse(null);
    }

    @Override
    @Modifying
    @Transactional
    @Query("DELETE  FROM RememberMeToken t WHERE t.username = ?1")
    void removeUserTokens(String username);
}
