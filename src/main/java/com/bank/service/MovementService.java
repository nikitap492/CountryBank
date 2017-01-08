package com.bank.service;

import com.bank.domain.Bill;
import com.bank.domain.services.Movement;

import java.util.List;

/**
 * {@link com.bank.service.impl.MovementServiceImpl}
 */
public interface MovementService {

    void save(Movement... movement);

    List<Movement> findByBill(Bill bill);

    Movement findById(Long id);

    void makeTransfer(Bill in, Bill out, Double money, String message);

    void makePay(Bill curBill, Double pay);
}
