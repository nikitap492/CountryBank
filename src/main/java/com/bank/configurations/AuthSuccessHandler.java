package com.bank.configurations;


import com.bank.service.BlockerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Clear user attempts after successful authentication by {@link BlockerService}
 */
@Component
class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final int SESSION_TIME = 50000;
    private static final Logger log = LoggerFactory.getLogger(AuthFailureHandler.class);

    @Autowired
    private BlockerService service;

    @Autowired
    private RedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        service.success(authentication.getName());
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(SESSION_TIME);
        redirectStrategy.sendRedirect(request, response, "/");
        clearRequest(request);
        log.debug("Authentication was successful for " + authentication.getName());
    }

    private void clearRequest(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
