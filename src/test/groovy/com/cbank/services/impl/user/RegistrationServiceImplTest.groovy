package com.cbank.services.impl.user

import com.cbank.services.*
import com.cbank.validators.RegistrationValidator
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
@SpringBootTest
class RegistrationServiceImplTest extends Specification {

    def userService = Mock(UserService)
    def tokenService = Mock(TokenService)
    def clientService = Mock(ClientService)
    def accountService = Mock(AccountService)
    def messageService = Mock(MessageService)
    def registrationValidator = new RegistrationValidator(userService)

    def registrationService = new RegistrationServiceImpl(userService, tokenService,
            clientService, accountService, messageService, registrationValidator)



    def "confirm registration"() {
        given: def token = "token"
        when: registrationService.confirm(token)
        then: 1 * userService.enable(token)
    }

    def "check username or email is free"() {

    }
}
