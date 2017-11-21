package com.cbank.services.impl;

import com.bank.repositories.UserRepository;
import com.cbank.domain.Account;
import com.cbank.domain.RegistrationForm;
import com.cbank.domain.message.MessageTemplate;
import com.cbank.domain.security.BaseTokenType;
import com.cbank.domain.user.UserProjection;
import com.cbank.services.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final TokenService tokenService;
    private final ClientService clientService;
    private final ContactService contactService;
    private final AccountService accountService;
    private final MessageService messageService;


    @Override
    public Account register(RegistrationForm form) {
        val user = form.toUser();
        userService.save(user);

        val token = tokenService.create(user.getUsername(), BaseTokenType.REGISTRATION);

        val client  = form.toClient(user.getId());
        clientService.save(client);

        val contact = form.toContact(client.getId());
        contactService.save(contact);

        val account = new Account(client.getId());
        accountService.save(account);

        messageService.send(contact, MessageTemplate.REGISTRATION_CONFIRMATION,
                Map.of("user", user, "client", client,
                        "account", account,
                        "token", token) );

        log.debug("Account has been saved successfully: " + account);
        return account;
    }

    @Override
    public Account confirm() {
        return null;
    }
}
