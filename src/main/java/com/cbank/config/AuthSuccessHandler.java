package com.cbank.config;


import com.cbank.services.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final int SESSION_TIME = 50000;

    private final AuthenticationService authenticationService;
    private final RedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        authenticationService.success(authentication.getName());
        val session = request.getSession();
        session.setMaxInactiveInterval(SESSION_TIME);
        redirectStrategy.sendRedirect(request, response, "/");
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        log.debug("Authentication was successful for " + authentication.getName());
    }

}
