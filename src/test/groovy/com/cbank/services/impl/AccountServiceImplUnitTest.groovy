package com.cbank.services.impl

import com.cbank.domain.Account
import com.cbank.exceptions.InsufficientFundsException
import com.cbank.repositories.AccountRepository
import com.cbank.services.BalanceService
import com.cbank.services.TransactionService
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class AccountServiceImplUnitTest extends Specification {

    def balanceService = Mock(BalanceService)
    def transactionService = Mock(TransactionService)
    def accountRepository = Mock(AccountRepository)
    def accountService = new AccountServiceImpl(accountRepository, transactionService, balanceService)

    def "find current account"() {
        given:
        def account = new Account(clientId: 1)
        accountRepository.findByClientIdAndCurrentIsTrue(account.clientId) >> Optional.of(account)
        when:
        def target = accountService.current(account.clientId)
        then:
        target.get() == account
    }

    def "mark as current"() {
        given:
        def current = new Account(clientId: 1, current: true)
        def target = new Account(num: "000000000000000", current: false)
        accountRepository.findByNum(target.num) >> Optional.of(target)
        when:
        accountService.asCurrent(current, target.num)
        then:
        1 * accountRepository.save(current)
        1 * accountRepository.save(target)
        target.current
        !current.current
    }

    def "create: should not throw any exception"() {
        given:
        def current = new Account(clientId: 1, current: true)
        balanceService.balance(current) >> new BigDecimal(10_000)
        when:
        accountService.create(current)
        then:
        1 * transactionService.create(_)
        2 * accountRepository.save(_)
        !current.current
    }


    def "create: should  throw the exception"() {
        given:
        def current = new Account(clientId: 1, current: true)
        balanceService.balance(current) >> BigDecimal.ZERO
        when:
        accountService.create(current)
        then:
        thrown(InsufficientFundsException)
    }

    def "by clientId"() {
        given:
        def account = new Account(clientId: 1)
        accountRepository.findAllByClientId(account.clientId) >> [account]
        when:
        def target = accountService.byClient(account.clientId)
        then:
        !target.isEmpty()
        target.size() == 1
        target.iterator().next() == account
    }
}
