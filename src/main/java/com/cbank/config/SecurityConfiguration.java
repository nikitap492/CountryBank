package com.cbank.config;

import com.cbank.repositories.RememberMeTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final int TOKEN_VALIDITY_SEC = 1000000;

    private final RememberMeTokenRepository tokenRepository;
    private final AuthFailureHandler authFailureHandler;
    private final Sha256AuthenticationProvider authenticationProvider;


    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/sign", "/images/**", "/js/**", "/users/**", "/feedback/**",
                    "/confirm/**", "/forgot/**", "/personal/**", "/business/**",
                    "/css/**", "/messages/**", "/subscribers/**", "/fonts/**", "/contact/**").permitAll()
            .anyRequest().authenticated()
        .and()
        .formLogin()
            .loginPage("/sign")
            .usernameParameter("login")
            .passwordParameter("pass")
            .defaultSuccessUrl("/")
            .failureHandler(authFailureHandler)
        .and()
            .logout()
            .logoutSuccessUrl("/")
        .and()
            .rememberMe()
            .tokenRepository(tokenRepository)
            .tokenValiditySeconds(TOKEN_VALIDITY_SEC)
        .and()
            .csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

}
