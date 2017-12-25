package com.cbank.services.impl.message

import com.cbank.domain.Client
import com.cbank.domain.message.MessageTemplate
import com.cbank.domain.security.BaseToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 25.12.2017.
 */
@SpringBootTest
class MessageTextTemplateFactoryImplIntTest extends Specification {

    @Autowired
    MessageTextTemplateFactoryImpl templateFactory

    def "create: registration template"() {
        when:
        templateFactory.create(MessageTemplate.REGISTRATION_CONFIRMATION, [token: new BaseToken(token: "1234567890"), client: new Client(name: "Mr Smith")])
        then:
        noExceptionThrown()
    }

    def "create: access recovery"(){
        when:
        templateFactory.create(MessageTemplate.ACCESS_RECOVERY, [token: new BaseToken(token: "1234567890")])
        then:
        noExceptionThrown()
    }

}
