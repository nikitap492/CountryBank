package com.cbank.config

import com.cbank.domain.user.User
import com.cbank.exceptions.AuthenticationProcessException
import com.cbank.services.UserService
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

import static java.util.Optional.empty
import static java.util.Optional.of

class Sha256AuthenticationProviderUnitTest extends Specification {

    def userService = Mock(UserService)
    def authProvider = new Sha256AuthenticationProvider(userService)

    def "authenticate: throw exception"() {
        given:

        when:
        userService.byUsername("login") >> users
        userService.hashPassword(*_) >> "otherPassword"
        authProvider.authenticate(new TestingAuthenticationToken("login", "password"))
        then:
        thrown(AuthenticationProcessException)
        where:
        users << [empty(), of(new User(nonLocked: true, enabled: false)),
                 of(new User(nonLocked: false, enabled: true)), of(new User(nonLocked: true, enabled: true, password: "P@ssW0rD"))]

    }

    def "authenticate: ok"() {
        given:
        def user = new User(nonLocked: true, enabled: true, password: "P@ssW0rD")
        userService.byUsername("login") >>  of(user)
        userService.hashPassword(user, "password") >> "P@ssW0rD"
        when:
        authProvider.authenticate(new TestingAuthenticationToken("login", "password"))
        then:
        noExceptionThrown()

    }

    def "Supports"() {
        expect:
        !authProvider.supports(Object)
        and:
        authProvider.supports(UsernamePasswordAuthenticationToken)

    }
}
