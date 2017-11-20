package com.bank.service.impl;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.repositories.BillRepository;
import com.bank.service.BillService;
import com.bank.service.MovementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation for {@link BillService}
 */
@Slf4j
@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final MovementService movementService;

    /**
     * Method for saving {@param bill}
     * First of all method checks any bills of account
     * if account has no one then set {@param bill} as current
     */
    @Override
    public void save(Bill bill) {
        List<Bill> bills = findByAccount(bill.getAccount());
        if (bills.size() > 0) {
            billRepository.save(bill);
        } else setCurrent(bill);
        log.debug("Bill : " + bill + " has been saved");
    }

    @Override
    public Bill findByUuid(UUID uuid) {
        return billRepository.findByUuid(uuid);
    }

    @Override
    public Bill findByUuid(String uuid) {
        return findByUuid(UUID.fromString(uuid));
    }


    @Override
    public List<Bill> findByAccount(Account account) {
        return billRepository.findBillsByAccount(account);
    }


    /**
     * Opening new bill for  {@param account}
     * Operation costs 5.0 $
     */
    @Override
    @Transactional
    public void saveByAccount(Account account) {
        Bill newBill = new Bill(account);
        Bill curBill = getCurrentForAccount(account);
        log.debug("Current bill : " + curBill + " for account:" + account);
        if (curBill != null) {
            movementService.makeTransfer(billRepository.findByUuid(bankUUID), curBill, 5.0, "You opened new bill");
        }
        save(newBill);
    }

    /**
     * returns number of bills for account
     */
    @Override
    public int countByAccount(Account account) {
        return billRepository.countBillsByAccount(account);
    }

    /**
     * return current bill for {@param account}
     */
    @Override
    public Bill getCurrentForAccount(Account account) {
        return billRepository.getCurrentForAccount(account);
    }

    /**
     * return {@param bill} as current
     */
    @Override
    @Transactional
    public void setCurrent(Bill bill) {
        billRepository.cleanCurrentStatusByAccount(bill.getAccount());
        bill.setCurrent(true);
        billRepository.save(bill);
        log.debug("Current bill is " + bill.getUuid());
    }

    /**
     * {@code setCurrent(Bill bill)}
     */
    @Override
    public void setCurrent(String uuid) {
        this.setCurrent(findByUuid(uuid));
    }

    @Override
    public Bill findByUsername(String name) {
        return billRepository.findCurrentBillByUsername(name);
    }


}
