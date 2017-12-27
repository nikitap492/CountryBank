package com.cbank.controllers;

import com.cbank.domain.Client;
import com.cbank.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contains only get methods for navigation
 */

@Controller
@AllArgsConstructor
public class NavigationController {
    private final AccountService accountService;

    @GetMapping("/")
    public String init() {
        return "index";
    }

    @GetMapping("/personal")
    public String personal() {
        return "personal";
    }

    @GetMapping("/business")
    public String business() {
        return "business";
    }

    @GetMapping("/contact")
    public String investors() {
        return "contact";
    }

    @GetMapping("/sign")
    public String signIn() {
        return "sign";
    }

    @GetMapping("/forgot")
    public String reset() {
        return "reset";
    }

    @GetMapping("/confirm")
    public String confirm() {
        return "confirm";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/private")
    public String dbo(Client client, Model model) {
        model.addAttribute("accounts", accountService.byClient(client.getId()));
        return "private";
    }

}
