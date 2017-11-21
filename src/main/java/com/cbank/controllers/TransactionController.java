package com.cbank.controllers;

import com.cbank.domain.Account;
import com.cbank.domain.transaction.Transaction;
import com.cbank.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
@AggregationModel
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionCreationRequest request,
                                    @ModelAttribute Account account) {

        return ResponseEntity.ok(
                transactionService.create(request.toTransaction(account.getNum()))
        );
    }

    @Data
    private static class TransactionCreationRequest {
        private String recipient;
        private BigDecimal amount;
        private String details;

        Transaction toTransaction(String account) {
            return Transaction.builder()
                    .payer(account)
                    .recipient(recipient)
                    .amount(amount)
                    .details(details)
                    .build();
        }
    }

}
