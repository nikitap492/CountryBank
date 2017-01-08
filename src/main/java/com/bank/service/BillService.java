package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @link com.bank.service.impl.BillServiceImpl
 */

public interface BillService {

    /**
     * {@value bankUUID} and {@value governmentUUID} is important entity in application
     */
    UUID bankUUID = UUID.fromString("276fc627-9947-4c84-9fe0-054231a2ca8b");
    UUID governmentUUID = UUID.fromString("772e6147-1f32-4154-b8da-3f8cd120a8e2");

    void save(Bill bills);

    Bill findByUuid(UUID uuid);

    Bill findByUuid(String uuid);

    List<Bill> findByAccount(Account account);

    void setCurrent(Bill bill);

    void setCurrent(String uuid);

    Bill findByUsername(String name);

    void saveByAccount(Account account);

    int countByAccount(Account account);

    Bill getCurrentForAccount(Account account);


}
