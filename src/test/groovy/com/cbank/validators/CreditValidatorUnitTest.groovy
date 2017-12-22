package com.cbank.validators

import com.cbank.domain.credit.Credit
import com.cbank.exceptions.InsufficientFundsException
import com.cbank.exceptions.ValidationException
import com.cbank.repositories.CreditRepository
import com.cbank.services.BalanceService
import spock.lang.Specification

import static com.cbank.domain.credit.CreditType.*

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class CreditValidatorUnitTest extends Specification {

    def balanceService = Mock(BalanceService)
    def creditRepository = Mock(CreditRepository)
    def creditValidator = new CreditValidator(balanceService, creditRepository)


    def "validate: shouldn't throw any exception"() {
        given:
        def credit = new Credit(accountId: 10, initialAmount: 6_000, type: PERSONAL_CREDIT)
        creditRepository.findAllByAccountIdAndTypeIn(*_) >> []
        when:
        creditValidator.validate(credit)
        then:
        noExceptionThrown()
    }


    def "validate: should throw validation exception"() {
        given:
        creditRepository.findAllByAccountIdAndTypeIn(*_) >> []
        when:
        creditValidator.validate(new Credit(accountId: 10, initialAmount: amount, type: type))
        then:
        thrown(ValidationException)
        where:
        type | amount
        PERSONAL_CREDIT | BigDecimal.TEN
        PERSONAL_CREDIT | new BigDecimal(100_000)
        BUSINESS_CREDIT | new BigDecimal(1_000)
        BUSINESS_CREDIT | new BigDecimal(10_000_000)
    }

    def "validate: should throw insufficient funds exception"() {
        given:
        def credit = new Credit(accountId: 10, initialAmount: BigDecimal.TEN, type: BUSINESS_DEPOSIT)
        creditRepository.findAllByAccountIdAndTypeIn(*_) >> []
        balanceService.balance(credit.accountId) >> BigDecimal.ZERO
        when:
        creditValidator.validate(credit)
        then:
        thrown(InsufficientFundsException)
    }
}
