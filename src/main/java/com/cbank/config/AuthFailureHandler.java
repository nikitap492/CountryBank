package com.cbank.config;

import com.cbank.services.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
class AuthFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy;
    private AuthenticationService failureService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        val login = request.getParameter("login");
        log.debug("Authentication failed for " + login + "  : " + e.getMessage());
        val status = failureService.failed(login);
        redirectStrategy.sendRedirect(request, response, "sign?error=" + status.toString());
    }
}