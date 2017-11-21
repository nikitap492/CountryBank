package com.cbank.controllers;

import com.bank.domain.Bill;
import com.bank.service.BillService;
import com.bank.service.MovementService;
import com.bank.validators.MovementValidationAnswer;
import com.bank.validators.MovementValidator;
import com.bank.validators.ValidationResult;
import com.cbank.domain.Account;
import com.cbank.domain.Transaction;
import com.cbank.services.AccountService;
import com.cbank.services.TransactionService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
