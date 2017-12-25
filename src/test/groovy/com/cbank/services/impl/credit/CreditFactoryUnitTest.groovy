package com.cbank.services.impl.credit

import com.cbank.domain.Account
import com.cbank.domain.credit.CreditState
import com.cbank.domain.credit.CreditType
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 25.12.2017.
 */
class CreditFactoryUnitTest extends Specification {

    def creditFactory = new CreditFactory()

    def "create"() {
        given:
        def initialAmount = new BigDecimal(1000)
        when:
        def credit = creditFactory.create(new Account(id: 17), CreditType.PERSONAL_CREDIT, initialAmount, 5)
        then:
        credit.type == CreditType.PERSONAL_CREDIT
        credit.closedAt != null
        credit.initialAmount == initialAmount
        credit.state == CreditState.OPENED
        credit.numOfWithdraws == 5
        credit.accountId == 17
        credit.monthlySum == new BigDecimal(18)

    }
}
