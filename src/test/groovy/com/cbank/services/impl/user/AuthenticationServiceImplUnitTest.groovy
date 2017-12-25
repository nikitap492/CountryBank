package com.cbank.services.impl.user

import com.cbank.domain.user.User
import com.cbank.services.UserService
import spock.lang.Specification

import static com.cbank.services.AuthenticationService.UserFailStatus.BLOCK
import static com.cbank.services.AuthenticationService.UserFailStatus.WRONG

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class AuthenticationServiceImplUnitTest extends Specification {

    def userService = Mock(UserService)
    def authenticationService = new AuthenticationServiceImpl(userService)
    def username = "smith56"
    def user = new User(username: username)

    def "failed: user was not found"() {
        given: userService.byUsername(username)  >> Optional.empty()
        when: def fail =  authenticationService.failed(username)
        then: fail == WRONG
    }


    def "failed: password is incorrect"() {
        given: userService.byUsername(username)  >> Optional.of(user)
        when: def fail =  authenticationService.failed(username)
        then: fail == WRONG
    }

        def "failed: attemps are exceed"() {
        given:
        userService.byUsername(username)  >> Optional.of(user)
        authenticationService.attemptsRepository.put(username, 3)
        when: def fail =  authenticationService.failed(username)
        then: fail == BLOCK
    }

    def cleanup(){
        authenticationService.attemptsRepository.clear()
    }

    def "success"() {
    }
}
