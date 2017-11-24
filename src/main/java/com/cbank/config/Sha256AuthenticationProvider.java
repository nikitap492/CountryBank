package com.cbank.config;

import com.cbank.domain.user.User;
import com.cbank.exceptions.AuthenticationProcessException;
import com.cbank.services.UserService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Podshivalov N.A.
 * @since 23.11.2017.
 */
@Component
@AllArgsConstructor
public class Sha256AuthenticationProvider implements AuthenticationProvider{
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        val user = userService.byUsername(auth.getPrincipal().toString())
                .orElseThrow(() -> new AuthenticationProcessException(auth.getPrincipal() + " wasn't found"));

        validate(user, auth.getCredentials().toString());
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.emptyList());
    }

    private void validate(User user, String password){
        if(!user.isEnabled()) throw new AuthenticationProcessException("Your account hasn't been confirmed");
        if(!user.isNonLocked()) throw new AuthenticationProcessException("Your account was locked. Contact to us for more information");
        val hashed = userService.hashPassword(user, password);
        if(!user.getPassword().equals(hashed)) throw new AuthenticationProcessException("Your account hasn't been confirmed");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}
