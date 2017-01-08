package com.bank.repositories;

import com.bank.domain.security.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * {@link RememberMeTokenRepository} is a wrapper of {@link PersistentTokenRepository} that
 * using Spring Data {@link JpaRepository} and default methods in interface
 * {@link RememberMeToken} is a wrapper of {@link PersistentRememberMeToken}
 */
public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, String>, PersistentTokenRepository {

    /**
     * Method converts {}@param token} into {@RememberMeToken} and after save it
     */
    @Override
    default void createNewToken(PersistentRememberMeToken token) {
        save(new RememberMeToken(token));
    }

    /**
     * Query for updating token
     */
    @Override
    @Modifying
    @Transactional
    @Query("UPDATE RememberMeToken t SET t.tokenValue = :tokenValue, t.date= :lastUsed WHERE t.series = :series")
    void updateToken(@Param("series") String series,
                     @Param("tokenValue") String tokenValue,
                     @Param("lastUsed") Date lastUsed);

    /**
     * @param series is id of {@link RememberMeToken}
     *               If token wasn't founded, method {@code findOne} would thrown {@throws NullPointerException}.
     *               In this case returns null
     */
    @Override
    default PersistentRememberMeToken getTokenForSeries(String series) {
        try {
            return findOne(series).getToken();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Query for deleting  token
     */
    @Override
    @Modifying
    @Transactional
    @Query("DELETE  FROM RememberMeToken t WHERE t.username = :username")
    void removeUserTokens(@Param("username") String username);
}
