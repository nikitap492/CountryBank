package com.bank.service.impl;

import com.bank.domain.Bill;
import com.bank.domain.services.Movement;
import com.bank.repositories.MovementRepository;
import com.bank.service.BillService;
import com.bank.service.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bank.domain.services.Direction.IN;
import static com.bank.domain.services.Direction.OUT;

/**
 * @author Poshivalov Nikita
 * @since 28.11.2016.
 */
@Service
public class MovementServiceImpl implements MovementService {

    private static final Logger log = LoggerFactory.getLogger(MovementService.class);

    @Autowired
    private MovementRepository repository;

    @Autowired
    private BillService billService;


    @Override
    public void save(Movement... movements) {
        for (Movement movement : movements) {
            Bill bill = movement.getBill();
            if (movement.getDirection() == IN)
                bill.income(movement.getQuantity());
            else bill.expense(movement.getQuantity());
            billService.save(bill);
            repository.save(movement);
            log.debug("Movement : " + movement + " has been saved");
        }
    }

    @Override
    public List<Movement> findByBill(Bill bill) {
        return repository.findByBill(bill);
    }

    @Override
    public Movement findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public void makeTransfer(Bill in, Bill out, Double money, String reason) {
        save(new Movement(out, OUT, money, reason));
        save(new Movement(in, IN, money, reason));
    }

    @Override
    public void makePay(Bill curBill, Double pay) {
        double commission = pay * 0.01;
        log.debug("Pay :" + pay + ", commission :" + commission);
        Bill bankBill = billService.findByUuid(BillService.bankUUID);
        Bill govBill = billService.findByUuid(BillService.governmentUUID);
        makeTransfer(govBill, curBill, pay, "Payment");
        makeTransfer(bankBill, curBill, commission, "Commission for payment");
    }
}
