package com.cbank.services.impl.tariff

import com.cbank.domain.transaction.Transaction
import com.cbank.services.AccountService
import spock.lang.Shared
import spock.lang.Specification

import java.math.RoundingMode

import static java.math.BigDecimal.TEN
import static java.math.BigDecimal.ZERO

/**
 * @author Podshivalov N.A. 
 * @since 25.12.2017.
 */
class TariffResolverUnitTest extends Specification {


    def tariffResolver = new TariffResolver()

    @Shared
    def accountNum = "123456789076821"

    def "evaluate"() {
        when:
        def evaluated = tariffResolver.evaluate(new Transaction(recipient: recipient, amount: amount, payer: "1234567890"))
        then:
        evaluated.compareTo(commission) == 0
        where:
        recipient                         | amount                    | commission
        AccountService.GOVERNMENT_ACCOUNT | new BigDecimal(123_456)   | TEN
        AccountService.GOVERNMENT_ACCOUNT | new BigDecimal(500)       | TEN
        AccountService.GOVERNMENT_ACCOUNT | new BigDecimal(9_999_999) | TEN
        AccountService.BANK_ACCOUNT       | new BigDecimal(123_456)   | ZERO
        AccountService.BANK_ACCOUNT       | new BigDecimal(500)       | ZERO
        AccountService.BANK_ACCOUNT       | new BigDecimal(9_999_999) | ZERO
        accountNum                        | new BigDecimal(100)       | new BigDecimal(7)
        accountNum                        | new BigDecimal(40)        | new BigDecimal(1.2).setScale(2, RoundingMode.CEILING)
        accountNum                        | new BigDecimal(15000)     | new BigDecimal(2250)
    }
}