package com.cbank.controllers;

import com.cbank.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Slf4j
@Controller
@AllArgsConstructor
@AggregationModel
@RequestMapping("/accounts/{accountNum}/statement")
public class StatementController {
    private final TransactionService transactionService;

    @GetMapping
    public String movements(@PathVariable String accountNum, Model model) {
        model.addAttribute("transactions", transactionService.byAccount(accountNum));
        return "included/transactions :: statement";
    }



}
