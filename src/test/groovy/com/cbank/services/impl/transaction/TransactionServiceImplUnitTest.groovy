package com.cbank.services.impl.transaction

import com.cbank.domain.Account
import com.cbank.domain.credit.Credit
import com.cbank.domain.credit.CreditType
import com.cbank.domain.transaction.Transaction
import com.cbank.exceptions.InsufficientFundsException
import com.cbank.repositories.TransactionRepository
import com.cbank.services.BalanceService
import com.cbank.services.TariffService
import spock.lang.Specification

import static com.cbank.services.AccountService.BANK_ACCOUNT
import static java.math.BigDecimal.TEN
import static java.math.BigDecimal.ZERO

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class TransactionServiceImplUnitTest extends Specification {

    def balanceService = Mock(BalanceService)
    def tariffService = Mock(TariffService)
    def transactionRepository = Mock(TransactionRepository)
    def transactionService = new TransactionServiceImpl(balanceService, tariffService, transactionRepository)

    def accountNum = "1111111111111111"
    def first = new Transaction(payer: BANK_ACCOUNT, recipient: accountNum, amount: new BigDecimal(145))
    def second = new Transaction(payer: accountNum, recipient: BANK_ACCOUNT, amount: new BigDecimal(80))
    def third = new Transaction(payer: accountNum, recipient: BANK_ACCOUNT, amount: new BigDecimal(55))

    def "create general transaction"() {
        given:
        balanceService.balance(second.payer) >> new BigDecimal(200)
        tariffService.evaluate(_) >> TEN
        when: transactionService.create(second)
        then: 2 * transactionRepository.save(_)
    }

    def "create general transaction: throw InsufficientFundsException"() {
        given:
        balanceService.balance(second.payer) >> ZERO
        tariffService.evaluate(_) >> TEN
        when: transactionService.create(second)
        then:
        0 * transactionRepository.save(_)
        thrown(InsufficientFundsException)
    }

    def "credit withdraw"() {
        when: transactionService.creditWithdraw(new Account(num: accountNum), new Credit(initialAmount: TEN, type: CreditType.BUSINESS_CREDIT))
        then: 1 * transactionRepository.save(_)

    }

    def "create credit"() {
        when: transactionService.credit(new Account(num: accountNum), new Credit(initialAmount: TEN, type: CreditType.BUSINESS_CREDIT))
        then: 1 * transactionRepository.save(_)
    }

    def "create deposite"() {
        given:
        def account = new Account(num: accountNum)
        balanceService.balance(*_) >> new BigDecimal(10000)
        when: transactionService.credit(account, new Credit(initialAmount: new BigDecimal(1000), type: CreditType.BUSINESS_DEPOSIT))
        then: 1 * transactionRepository.save(_)
    }

    def "create deposite: throw InsufficientFundsException"() {
        given:
        def account = new Account(num: accountNum)
        balanceService.balance(*_) >> ZERO
        when: transactionService.credit(account, new Credit(initialAmount: new BigDecimal(1000), type: CreditType.BUSINESS_DEPOSIT))
        then:
        0 * transactionRepository.save(_)
        thrown(InsufficientFundsException)
    }

    def "load statement"() {
        given:
        transactionRepository.findAllByAccountNum(accountNum) >> [first, second, third]
        expect: transactionService.byAccount(accountNum).size() == 3
    }
}
