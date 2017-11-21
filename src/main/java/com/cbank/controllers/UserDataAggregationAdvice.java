package com.cbank.controllers;


import com.bank.domain.Bill;
import com.cbank.domain.Account;
import com.cbank.domain.Client;
import com.cbank.services.AccountService;
import com.cbank.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@AllArgsConstructor
public class UserDataAggregationAdvice {
    private final AccountService accountService;
    private final ClientService clientService;


    @ModelAttribute
    public Client client(Authentication authentication) {
        return Optional.ofNullable(authentication)
                .flatMap(auth -> clientService.byUserId(auth.getName()))
                .orElse(null);
    }

    @ModelAttribute("account")
    public Account account(@ModelAttribute Client client) {
        return accountService.current(client.getId())
                .orElse(null);
    }

}
