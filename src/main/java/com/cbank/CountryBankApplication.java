package com.cbank;

import com.cbank.config.MailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

@Slf4j
@SpringBootApplication
public class CountryBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountryBankApplication.class);
    }


    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }

    @Bean
    public MailProperties emailProperties(){
        return new MailProperties();
    }

}
