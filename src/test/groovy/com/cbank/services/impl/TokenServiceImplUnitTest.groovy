package com.cbank.services.impl

import com.cbank.domain.security.BaseToken
import com.cbank.domain.security.BaseTokenType
import com.cbank.exceptions.TokenExpiredException
import com.cbank.repositories.BaseTokenRepository
import spock.lang.Specification

import javax.persistence.EntityNotFoundException
import java.time.LocalDateTime

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class TokenServiceImplUnitTest extends Specification {

    def baseTokenRepository = Mock(BaseTokenRepository)
    def tokenService = new TokenServiceImpl(baseTokenRepository)

    def "create token"() {
        when:
        tokenService.create("username", BaseTokenType.REGISTRATION)
        then:
        1 * baseTokenRepository.save(_)
    }

    def "check that job searching expired token works correctly"() {
        when:
        tokenService.checkForExpired()
        then:
        1 * baseTokenRepository.expire(_)
    }

    def "get token and invalidate it"() {
        given:
        def uuid = UUID.randomUUID().toString()
        def token = new BaseToken(token: uuid, createdAt: LocalDateTime.now(), valid: true)
        baseTokenRepository.findOne(uuid) >> token
        when:
        tokenService.get(uuid)
        then:
        noExceptionThrown()
        !token.valid
        1 * baseTokenRepository.save(token)
    }

    def "getToken should not found token and throw exception"() {
        given:
        def uuid = UUID.randomUUID().toString()
        baseTokenRepository.findOne(uuid) >> null
        when:
        tokenService.get(uuid)
        then:
        thrown(EntityNotFoundException)
    }

    def "getToken get expired token and throw exception"() {
        given:
        def uuid = UUID.randomUUID().toString()
        def token = new BaseToken(token: uuid, createdAt: LocalDateTime.now().minusHours(1), valid: true)
        baseTokenRepository.findOne(uuid) >> token
        when:
        tokenService.get(uuid)
        then:
        thrown(TokenExpiredException)
    }
}
