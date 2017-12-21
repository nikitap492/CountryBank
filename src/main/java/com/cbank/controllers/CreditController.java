package com.cbank.controllers;


import com.cbank.domain.Account;
import com.cbank.domain.credit.CreditType;
import com.cbank.services.CreditService;
import com.cbank.services.impl.credit.CreditFactory;
import com.cbank.validators.CreditValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/credits")
@AggregationModel
public class CreditController {
    private final CreditService creditService;
    private final CreditValidator creditValidator;
    private final CreditFactory creditFactory;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CreditRequest request, @ModelAttribute Account account) {
        val credit = creditFactory.create(account, request.type, request.amount, request.numOfMonths);
        creditValidator.validate(credit);
        return ok(creditService.create(credit));
    }

    @Data
    private static class CreditRequest{
        private CreditType type;
        private Integer numOfMonths;
        private BigDecimal amount;
    }


}
