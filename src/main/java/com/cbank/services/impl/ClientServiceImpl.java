package com.cbank.services.impl;

import com.cbank.domain.Client;
import com.cbank.domain.message.MessageTemplate;
import com.cbank.domain.security.BaseTokenType;
import com.cbank.repositories.ClientRepository;
import com.cbank.services.ClientService;
import com.cbank.services.MessageService;
import com.cbank.services.TokenService;
import com.cbank.utils.MapUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.google.common.collect.Maps.immutableEntry;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final TokenService tokenService;
    private final MessageService messageService;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> byUserId(String userId) {
        return clientRepository.findByUserId(userId);
    }

    @Override
    public void accessRecovery(String loginOrEmail) {
        val client = clientRepository.findByUserId(loginOrEmail)
                .orElseGet(() -> clientRepository.findByEmail(loginOrEmail)
                        .orElseThrow(EntityNotFoundException::new)); //java 9 Optional#or

        val token = tokenService.create(client.getUserId(), BaseTokenType.RESET_PASSWORD);
        messageService.send(client.getEmail(), MessageTemplate.ACCESS_RECOVERY,
                MapUtils.from(
                        immutableEntry("token", token),
                        immutableEntry("client", client)
                )
        );

    }
}
