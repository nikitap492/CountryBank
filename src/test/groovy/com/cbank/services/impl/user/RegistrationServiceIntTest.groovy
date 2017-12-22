package com.cbank.services.impl.user

import com.cbank.domain.RegistrationForm
import com.cbank.services.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
@SpringBootTest
class RegistrationServiceIntTest extends Specification {
    
    @Autowired
    RegistrationService registrationService

    def "register: without exceptions"() {
        when:
        def account = registrationService.register(new RegistrationForm(name: "James Smith", address: "Ocean Avenue, 5",
                username: "smith65", password: "password", email: "email65@mail.com" ))
        then:
        noExceptionThrown()
        account?.num
    }
}
