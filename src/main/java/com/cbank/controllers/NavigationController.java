package com.cbank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Contains only get methods for navigation
 */

@Controller
public class NavigationController {

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

    @GetMapping("/reset_password")
    public String reset() {
        return "reset";
    }

    @GetMapping("/confirm")
    public String confirm() {
        return "confirm";
    }

}
