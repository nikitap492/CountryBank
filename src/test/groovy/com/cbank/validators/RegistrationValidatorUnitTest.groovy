package com.cbank.validators

import com.cbank.domain.RegistrationForm
import com.cbank.exceptions.ValidationException
import com.cbank.services.UserService
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class RegistrationValidatorUnitTest extends Specification {

    def userService = Mock(UserService)
    def registrationValidator = new RegistrationValidator(userService)

    def "validate: shouldn't throw any exception"() {
        given:
        def form = new RegistrationForm(username: "smith65", password: "qwerty", email: "smith1865@mail.com", address: "Ocean Avenue, 5", name: "James Smith")
        userService.byUsername(form.username) >> Optional.empty()
        when:
        registrationValidator.validate(form)
        then:
        noExceptionThrown()
    }

    def "validate: should throw validation exception"() {
        given:
        def form = new RegistrationForm(username: username, password: password, email: email, address: address, name: name)
        userService.byUsername(form.username) >> Optional.empty()
        when:
        registrationValidator.validate(form)
        then:
        thrown(ValidationException)
        where:
        username   | password | email                | address           | name
        null       | "qwerty" | "smith1865@mail.com" | "Ocean Avenue, 5" | "James Smith"
        "sm"       | "qwerty" | "smith1865@mail.com" | "Ocean Avenue, 5" | "James Smith"
        "smitty:)" | "qwerty" | "smith1865@mail.com" | "Ocean Avenue, 5" | "James Smith"
        "smith65"  | "q"      | "smith1865@mail.com" | "Ocean Avenue, 5" | "James Smith"
        "smith65"  | null     | "smith1865@mail.com" | "Ocean Avenue, 5" | "James Smith"
        "smith65"  | "qwerty" | "smith1865@mail"     | "Ocean Avenue, 5" | "James Smith"
        "smith65"  | "qwerty" | "smith1865@mail.com" | "5"               | "James Smith"
        "smith65"  | "qwerty" | "smith1865@mail.com" | "Ocean Avenue,5"  | "s"
        "smith65"  | "qwerty" | "smith1865@mail.com" | "Ocean Avenue,5"  | null
    }
}
