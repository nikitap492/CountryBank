package com.cbank.services.impl

import com.cbank.domain.Client
import com.cbank.domain.message.MessageTemplate
import com.cbank.domain.security.BaseTokenType
import com.cbank.repositories.ClientRepository
import com.cbank.services.MessageService
import com.cbank.services.TokenService
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class ClientServiceImplUnitTest extends Specification {

    def messageService = Mock(MessageService)
    def tokenService = Mock(TokenService)
    def clientRepository = Mock(ClientRepository)
    def clientService = new ClientServiceImpl(clientRepository, tokenService, messageService)

    def "save"() {
        given: def client = new Client()
        when: clientService.save(client)
        then: 1 * clientRepository.save(client)
    }

    def "get client by username"() {
        given:
        def client = new Client(userId: "smith65")
        clientRepository.findByUserId(client.userId) >> Optional.of(client)
        when:
        def target = clientService.byUserId(client.userId)
        then:
        target.get() == client
    }

    def "access recovery"() {
        given:
        def client = new Client(email: "smith65@mail.com", userId: "smith65")
        clientRepository.findByUserId(client.userId) >> Optional.of(client)
        clientRepository.findByEmail(client.email) >> Optional.of(client)

        when: "by email"
        clientService.accessRecovery("smith65")
        then:
        1 * tokenService.create(client.userId, BaseTokenType.RESET_PASSWORD)
        1 * messageService.send(client.email, MessageTemplate.ACCESS_RECOVERY, _)

        when: "by username"
        clientService.accessRecovery("smith65")
        then:
        1 * tokenService.create(client.userId, BaseTokenType.RESET_PASSWORD)
        1 * messageService.send(client.email, MessageTemplate.ACCESS_RECOVERY, _)

    }
}
