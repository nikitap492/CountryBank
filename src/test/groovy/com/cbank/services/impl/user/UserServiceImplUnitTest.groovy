package com.cbank.services.impl.user

import com.cbank.domain.security.BaseToken
import com.cbank.domain.security.BaseTokenType
import com.cbank.domain.user.User
import com.cbank.repositories.UserRepository
import com.cbank.services.TokenService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class UserServiceImplUnitTest extends Specification {

    def tokenService = Mock(TokenService)
    def userRepository = Mock(UserRepository)
    def userService = new UserServiceImpl(userRepository, tokenService)


    def "save"() {
        given : def user = new User()
        when: userService.save(user)
        then: 1 * userRepository.save(user)
    }

    def "find by username"() {
        given:
        def user = new User(username: "username")
        userRepository.findByUsername(user.username) >> Optional.of(user)
        expect:
        userService.byUsername(user.username).get() == user
    }

    def "reset password"() {
        given:
        def token = UUID.randomUUID().toString()
        def oldPassword = "password"
        def user = new User(username: "username", password: oldPassword)
        tokenService.get(token) >> BaseToken.of(user.username, BaseTokenType.RESET_PASSWORD)
        userRepository.findByUsername(user.username) >> Optional.of(user)
        userRepository.save(user) >> user
        when:
        userService.resetPassword(token, "newPassword")
        then:
        user.password != oldPassword
    }

    def "enable"() {
        given:
        def token = UUID.randomUUID().toString()
        def user = new User(enabled: false, username: "username")
        tokenService.get(token) >> BaseToken.of(user.username, BaseTokenType.REGISTRATION)
        userRepository.findByUsername(user.username) >> Optional.of(user)
        when:
        userService.enable(token)
        then:
        1 * userRepository.save(user)
        user.enabled
    }

    def "lock"() {
        given:
        def user = new User(nonLocked: true)
        when:
        userService.lock(user)
        then:
        1 * userRepository.save(user)
        !user.nonLocked

    }

    def "loadUserByUsername: should return user"() {
        given:
        def username = "username"
        userRepository.findByUsername(username) >> Optional.of(new User())
        when:
        def user = userService.loadUserByUsername(username)
        then:
        noExceptionThrown()
        user != null

    }

    def "loadUserByUsername: should throw exception"() {
        given:
        def username = "username"
        userRepository.findByUsername(username) >> Optional.empty()
        when:
        userService.loadUserByUsername(username)
        then:
        thrown(UsernameNotFoundException)

    }
}
