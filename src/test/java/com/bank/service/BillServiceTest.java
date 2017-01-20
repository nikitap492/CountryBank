package com.bank.service;

import com.bank.Application;
import com.bank.domain.Account;
import com.bank.domain.Bill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static com.bank.DataTest.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class BillServiceTest {

    @Autowired
    @Spy
    private BillService billService;

    @Autowired
    private AccountService accountService;


    @Test
    public void shouldSaveBill() {
        final int before = billService.findByAccount(jimmyAcc).size();
        Bill bill = new Bill(jimmyAcc, 1.0);
        billService.save(bill);
        final int after = billService.findByAccount(jimmyAcc).size();
        assertEquals(before + 1, after);
    }

    @Test
    public void shouldFindByUUID() {
        final UUID uuid = bartBill.getUuid();
        final Bill billByUuid = billService.findByUuid(uuid);
        assertEquals(bartBill, billByUuid);
    }

    @Test
    public void shouldFindByUUIDString() {
        billService.findByUuid(bartBill.getUuid().toString());
        verify(billService, times(1)).findByUuid(bartBill.getUuid());
    }

    @Test
    public void shouldFindBillsByAccount() {
        Bill first = new Bill(susieAcc, 2.0);
        Bill second = new Bill(susieAcc, 12.0);
        billService.save(first);
        billService.save(second);
        final List<Bill> bills = billService.findByAccount(susieAcc);
        assertTrue(bills.size() >= 2);
        assertTrue(bills.contains(first));
        assertTrue(bills.contains(second));
    }

    @Test
    public void shouldSaveByAccount() {
        Double before = billService.getCurrentForAccount(bartAcc).getMoney();
        billService.saveByAccount(bartAcc);
        verify(billService, times(2)).getCurrentForAccount(bartAcc);
        Double after = billService.getCurrentForAccount(bartAcc).getMoney();
        Double sum = 5.0 + after;
        assertEquals(before, sum);
    }

    @Test
    public void shouldCountBillsByAccount() {
        assertEquals(billService.findByAccount(bartAcc).size(), billService.countByAccount(bartAcc));
    }


    @Test
    public void shouldSetCurrentBill() {
        Bill bill = new Bill(susieAcc, 1000.);
        Bill bill2 = new Bill(susieAcc, 1000.);
        Bill bill3 = new Bill(susieAcc, 1000.);
        billService.save(bill);
        billService.save(bill2);
        billService.save(bill3);
        List<Bill> bills = billService.findByAccount(susieAcc);
        Bill b = bills.get(1);
        assertTrue(!b.getCurrent());
        billService.setCurrent(b);
        UUID uuid = b.getUuid();
        Bill billByUuid = billService.findByUuid(uuid);
        assertTrue(billByUuid.getCurrent());
    }

    @Test
    public void shouldGetCurrentBillByAccount() {
        bartBill.setCurrent(true);
        Bill bill = billService.getCurrentForAccount(bartAcc);
        assertEquals(bartBill, bill);
    }


    @Test
    public void shouldSetCurrentBillForFirstBill() {
        Account account = new Account(kurtUser, kurt, kurt);
        accountService.save(account);
        Bill bill = new Bill(account, 20.0);
        UUID uuid = bill.getUuid();
        billService.save(bill);
        Bill billByUuid = billService.findByUuid(uuid);
        assertTrue(billByUuid.getCurrent());
    }

    @Test
    public void shouldFindCurrentBillByUsername() {
        Bill billByUsername = billService.findByUsername(bart);
        assertNotNull(billByUsername);
        assertTrue(billByUsername.getCurrent());
    }

    @Test
    public void shouldSetBillAsCurrentByUUIDString() {
        billService.setCurrent(BillService.governmentUUID.toString());
        Bill gov = billService.findByUuid(BillService.governmentUUID);
        verify(billService, times(1)).setCurrent(gov);
    }


}
