package com.bank.repositories;

import com.bank.domain.Bill;
import com.bank.domain.services.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByBill(Bill bill);

}
