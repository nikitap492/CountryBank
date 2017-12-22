package com.cbank.validators

import com.cbank.domain.message.Feedback
import com.cbank.exceptions.ValidationException
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class FeedbackValidatorUnitTest extends Specification {
    def feedbackValidator = new FeedbackValidator()

    def "validate: throw validation exception"() {
        when:
        feedbackValidator.validate(new Feedback(name, email , body))
        then:
        thrown(ValidationException)
        where:
        name       | email                | body
        "Mr Smith" | "smith1873@mail.com" | null
        "Mr Smith" | "smith1873@mail.com" | ""
        "Mr Smith" | "smith1873"          | "Hello! I want to invest in your bank"
        "M"        | null                 | "Hello! I want to invest in your bank"
        "Mr^smith" | "smith1873@mail.com" | "Hello! I want to invest in your bank"
        null       | "smith1873@mail.com" | "Hello! I want to invest in your bank"
    }

    def "validate: shouldn't throw any exception"() {
        when:
        feedbackValidator.validate(new Feedback("Mr Smith", "smith1873@mail.com", "Hello! I want to invest in your bank"))
        then:
        noExceptionThrown()
    }
}
