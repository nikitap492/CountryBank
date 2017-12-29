package com.cbank.config

import com.cbank.services.AuthenticationService
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.RedirectStrategy
import spock.lang.Specification

class AuthFailureHandlerUnitTest extends Specification {

    def authenticationService = Mock(AuthenticationService)
    def redirectStrategy = Mock(RedirectStrategy)
    def authFailureHandler = new AuthFailureHandler(redirectStrategy, authenticationService)

    def "OnAuthenticationFailure"() {
        given:
        def login = "login"
        def r = new MockHttpServletRequest()
        r.setParameter("login", login)
        authenticationService.failed(login) >> AuthenticationService.UserFailStatus.WRONG
        when:
        authFailureHandler.onAuthenticationFailure(r, new MockHttpServletResponse(), new UsernameNotFoundException("text"))
        then:
        1 * redirectStrategy.sendRedirect(*_)
    }
}
