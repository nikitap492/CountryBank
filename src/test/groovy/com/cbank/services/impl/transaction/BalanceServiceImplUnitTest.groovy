package com.cbank.services.impl.transaction

import com.cbank.domain.Account
import com.cbank.domain.transaction.Transaction
import com.cbank.repositories.AccountRepository
import com.cbank.repositories.TransactionRepository
import spock.lang.Specification

import static com.cbank.services.AccountService.BANK_ACCOUNT

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class BalanceServiceImplUnitTest extends Specification {

    def transactionRepository = Mock(TransactionRepository)
    def accountRepository = Mock(AccountRepository)
    def balanceService = new BalanceServiceImpl(transactionRepository, accountRepository)
    def accountNum = "12341234123412"
    def first = new Transaction(payer: BANK_ACCOUNT, recipient: accountNum, amount: new BigDecimal(145))
    def second = new Transaction(payer: accountNum, recipient: BANK_ACCOUNT, amount: new BigDecimal(80))
    def third = new Transaction(payer: accountNum, recipient: BANK_ACCOUNT, amount: new BigDecimal(55))

    def "balance"() {
        given:
        accountRepository.getOne(1L) >> new Account(num: accountNum)
        transactionRepository.findAllByAccountNum(accountNum) >> [first, second, third]
        when:
        def balance = balanceService.balance(1L)
        then:
        balance == BigDecimal.TEN
    }
}
