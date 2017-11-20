package com.cbank.services.impl;


import com.cbank.domain.security.BaseToken;
import com.cbank.domain.security.BaseTokenType;
import com.cbank.repositories.BaseTokenRepository;
import com.cbank.services.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Slf4j
@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final BaseTokenRepository baseTokenRepository;

    @Override
    public BaseToken create(String username, BaseTokenType tokenType) {
        val token = BaseToken.of(username, tokenType);
        baseTokenRepository.save(token);
        log.debug("Token has been created " + token);
        return token;
    }

    @Override
    public Optional<BaseToken> byId(String id) {
        return Optional.ofNullable(baseTokenRepository.findOne(id));
    }

    @Override
    public BaseToken invalidate(BaseToken token) {
        token.setValid(false);
        return baseTokenRepository.save(token);
    }

    @Override
    @Scheduled(cron = "${schedule.cron}")
    @Transactional
    public int checkForExpired() {
        log.debug("Schedule check for tokens");
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        int numOfExpired = baseTokenRepository.expire(time);
        log.debug(numOfExpired + " tokens has been updated");
        return numOfExpired;
    }
}
