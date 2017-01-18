package com.bank.configurations;

import com.bank.service.BlockerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class was needed for redirection to {@code "sign?error=true"}
 * Also, for blocking user after few wrong attempts by {@link BlockerService}
 */
@Component
class AuthFailureHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthFailureHandler.class);

    @Autowired
    private RedirectStrategy redirectStrategy;

    @Autowired
    private BlockerService service;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String login = request.getParameter("login");
        log.debug("Authentication error for " + login + "  : " + e.getMessage());
        BlockerService.UserFailStatus status = service.failed(login);
        redirectStrategy.sendRedirect(request, response, "sign?error=" +status.toString());
    }
}