package com.bank.controllers;

import com.bank.domain.other.RegistrationToken;
import com.bank.validators.TokenValidator;
import com.bank.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Contains only get methods for navigation
 */

@Controller
public class NavigationController {

    @RequestMapping(value = "/", method = GET)
    public String init() {
        return "index";
    }

    @RequestMapping(value = "/personal", method = GET)
    public String personal() {
        return "personal";
    }

    @RequestMapping(value = "/business", method = GET)
    public String business() {
        return "business";
    }

    @RequestMapping(value = "/contact", method = GET)
    public String investors() {
        return "contact";
    }

    @RequestMapping(value = "/sign", method = GET)
    public String signIn() {
        return "sign";
    }

    @RequestMapping(value = "/reset_password", method = GET)
    public String reset() {
        return "reset";
    }

    @RequestMapping(value = "/confirm", method = GET)
    public String confirm() {
        return "confirm";
    }

}
