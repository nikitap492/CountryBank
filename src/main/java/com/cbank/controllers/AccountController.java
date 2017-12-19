package com.cbank.controllers;


import com.cbank.domain.Account;
import com.cbank.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    @PatchMapping
    public ResponseEntity<?> setCurrent(@RequestBody MarkAsCurrentRequest request,
                                        @ModelAttribute("account") Account current) {
        return ResponseEntity.ok(
                accountService.asCurrent(current, request.accountNum)
        );
    }

    @Data
    private static class MarkAsCurrentRequest{
        private String accountNum;
    }


}
