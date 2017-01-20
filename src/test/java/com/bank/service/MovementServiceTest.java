package com.bank.service;

import com.bank.Application;
import com.bank.domain.Bill;
import com.bank.domain.services.Movement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

import static com.bank.DataTest.*;
import static com.bank.domain.services.Direction.IN;
import static java.lang.Double.compare;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class MovementServiceTest {

    private static Bill bankBill;
    private static Bill govBill;

    @Autowired
    private MovementService movementService;

    @Autowired
    private BillService billService;

    @PostConstruct
    public void init() {
        bankBill = billService.findByUuid(BillService.bankUUID);
        govBill = billService.findByUuid(BillService.governmentUUID);

    }

    @Test
    public void shouldSaveMovement() {
        final int before = movementService.findByBill(jimmyBill).size();
        Double money = jimmyBill.getMoney();
        UUID uuid = jimmyBill.getUuid();
        Double income = 125.0;
        movementService.save(new Movement(jimmyBill, IN, income));
        Double sum = income + money;
        assertEquals(sum, billService.findByUuid(uuid).getMoney());
        final int after = movementService.findByBill(jimmyBill).size();
        assertEquals(before + 1, after);
    }


    @Test
    public void shouldFindMovementByBill() {
        double income = 25.0;
        Movement movement = new Movement(bartBill, IN, income);
        movementService.save(movement);
        List<Movement> movements = movementService.findByBill(bartBill);
        assertTrue(movements.contains(movement));
        assertTrue(movements.size() > 0);
    }


    @Test
    public void shouldFindMovementById() {
        List<Movement> movements = movementService.findByBill(bartBill);
        assertTrue(movements.contains(bartMov));
        assertEquals(bartMov, movementService.findById(bartMov.getId()));
    }

    @Test
    public void shouldMakeTransfer() {
        double money = 200.0;
        String reason = "For Test";
        double govMoneyBefore = govBill.getMoney();
        double bankMoneyBefore = bankBill.getMoney();
        movementService.makeTransfer(govBill, bankBill, money, reason);
        assertTrue(compare(govBill.getMoney() - govMoneyBefore, money) == 0);
        assertTrue(compare(bankMoneyBefore - bankBill.getMoney(), money) == 0);
    }

    @Test
    public void shouldMakePay() {
        double money = 200.0;
        double govMoneyBefore = govBill.getMoney();
        double bankMoneyBefore = bankBill.getMoney();
        double bartMoneyBefore = bartBill.getMoney();
        movementService.makePay(bartBill, money);
        System.out.println(govBill.getMoney() - govMoneyBefore);
        assertTrue(compare(bartMoneyBefore - bartBill.getMoney(), money + 0.01 * money) == 0);
        assertTrue(compare(billService.findByUuid(BillService.bankUUID).getMoney()
                - bankMoneyBefore, 0.01 * money) == 0);
        assertTrue(compare(billService.findByUuid(BillService.governmentUUID).getMoney()
                - govMoneyBefore, money) == 0);
    }

    @Test
    public void shouldFindById(){
        Movement byId = movementService.findById(bartMov.getId());
        assertEquals(bartMov, byId);
    }

}
