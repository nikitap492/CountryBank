package com.cbank.services.impl.credit

import com.cbank.domain.Account
import com.cbank.domain.credit.Credit
import com.cbank.domain.credit.CreditState
import com.cbank.repositories.AccountRepository
import com.cbank.repositories.CreditRepository
import com.cbank.services.TransactionService
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * @author Podshivalov N.A. 
 * @since 25.12.2017.
 */
class CreditServiceImplUnitTest extends Specification {

    def creditRepository = Mock(CreditRepository)
    def transactionService = Mock(TransactionService)
    def accountRepository = Mock(AccountRepository)
    def creditService = new CreditServiceImpl(creditRepository, transactionService, accountRepository)

    def "create withdraw"(){
        given:
        def account = new Account()
        def credit = new Credit(accountId: 10L, closedAt: LocalDateTime.now().plusDays(30))
        accountRepository.findOne(10L) >> account
        when:
        creditService.withdraw(credit)
        then:
        1 * transactionService.creditWithdraw(account, credit)

    }

    def "create withdraw and close credit"(){
        given:
        def account = new Account()
        def credit = new Credit(accountId: 10L, closedAt: LocalDateTime.now().minusHours(5))
        accountRepository.findOne(10L) >> account
        when:
        creditService.withdraw(credit)
        then:
        1 * transactionService.creditWithdraw(account, credit)
        1 * creditRepository.save(credit)
        credit.state == CreditState.CLOSED

    }

    def "create credit"(){
        def account = new Account()
        def credit = new Credit(accountId: 10L, closedAt: LocalDateTime.now().plusDays(30))
        accountRepository.findOne(10L) >> account
        when:
        creditService.create(credit)
        then:
        1 * transactionService.credit(account, credit)
        1 * creditRepository.save(credit)
    }


}
