package com.cbank.controllers;


import com.cbank.domain.Account;
import com.cbank.services.AccountService;
import com.cbank.services.BalanceService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Contains API for private room
 */
@Slf4j
@RestController
@AllArgsConstructor
@AggregationModel
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final BalanceService balanceService;

    @PostMapping
    public ResponseEntity<?> add(@ModelAttribute("account") Account current) {
        return ok(accountService.create(current));
    }

    @PatchMapping
    public ResponseEntity<?> setCurrent(@RequestBody MarkAsCurrentRequest request,
                                        @ModelAttribute("account") Account current) {
        return ok(accountService.asCurrent(current, request.accountNum));
    }

    @GetMapping("/{accountNum}/balance")
    public ResponseEntity<?> balance(@PathVariable String accountNum){
        //todo check whether the account number is belonged to user
        return ok(BalanceContainer.of(balanceService.balance(accountNum)));
    }

    @Data
    private static class MarkAsCurrentRequest{
        private String accountNum;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    private static class BalanceContainer{
        private BigDecimal balance;
    }


}
