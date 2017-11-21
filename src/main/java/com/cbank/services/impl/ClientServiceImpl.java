package com.cbank.services.impl;

import com.cbank.domain.Client;
import com.cbank.repositories.ClientRepository;
import com.cbank.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> byUserId(String userId) {
        return clientRepository.findByUserId(userId);
    }
}
