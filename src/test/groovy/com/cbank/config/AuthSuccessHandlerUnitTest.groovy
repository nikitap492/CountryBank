package com.cbank.config

import com.cbank.services.AuthenticationService
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.core.Authentication
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.WebAttributes
import spock.lang.Specification

class AuthSuccessHandlerUnitTest extends Specification {

    def redirectStategy = Mock(RedirectStrategy)
    def authenticationService = Mock(AuthenticationService)
    def authSuccessHandler = new AuthSuccessHandler(authenticationService, redirectStategy)

    def setup(){
    }

    def onAuthenticationSuccessTest(){
        given:
        def login = "login"
        def rq = new MockHttpServletRequest()
        def s = new MockHttpSession()
        rq.setSession(s)
        def rp = new MockHttpServletResponse()
        def auth = Mock(Authentication)
        auth.getName() >> login
        when:
        authSuccessHandler.onAuthenticationSuccess(rq, rp, auth)
        then:
        1 * authenticationService.success(login)
        1 * redirectStategy.sendRedirect(rq, rp, _)
        s.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) == null
    }


}
