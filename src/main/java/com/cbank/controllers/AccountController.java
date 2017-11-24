package com.cbank.controllers;


import com.cbank.domain.Account;
import com.cbank.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> add(@ModelAttribute("account") Account current) {
        return ResponseEntity.ok().body(
                accountService.create(current)
        );
    }

    @PatchMapping(value = "/current")
    public ResponseEntity<?> setCurrent(@RequestParam String accountNum,
                                        @ModelAttribute("account") Account current) {
        return ResponseEntity.ok(
                accountService.asCurrent(current, accountNum)
        );
    }


}
