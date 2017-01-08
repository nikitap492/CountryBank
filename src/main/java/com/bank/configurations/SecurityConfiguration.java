package com.bank.configurations;

import com.bank.repositories.RememberMeTokenRepository;
import com.bank.service.UserService;
import com.bank.service.impl.PersistenceUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Security configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final int TOKEN_VALIDITY_SEC = 1000000;

    @Autowired
    private UserService service;

    @Autowired
    private RememberMeTokenRepository tokenRepository;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/sign").anonymous()
                .antMatchers("/private").authenticated()
                .antMatchers("/api/bill/**").authenticated()
                .antMatchers("/api/movement/**").authenticated()
                .antMatchers("/api/account/**").authenticated()
                .antMatchers("/api/credit/**").authenticated()
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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder);
    }

    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }

}
