package com.bank.controllers;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.service.AccountService;
import com.bank.service.BillService;
import com.bank.service.MovementService;
import com.bank.validators.MovementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Contains API for private room
 */

@Controller
public class PrivateController {

    private static final Logger log = LoggerFactory.getLogger(PrivateController.class);

    @Autowired
    private BillService billService;

    @Autowired
    private MovementService movementService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MovementValidator validator;

    @ModelAttribute("account")
    private Account account(Principal principal) {
        return principal == null ? null : accountService.findByUsername(principal.getName());
    }

    /**
     * Create new bill for {@param account}
     */
    @RequestMapping(value = "/api/bill/new", method = POST)
    @ResponseBody
    public ResponseEntity<String> addBill(@ModelAttribute("account") Account account) {
        if (!validator.validatePayForNewBill(account))
            return ResponseEntity.badRequest().body("You have not enough money. For opening new bill you must pay 5.0$");
        billService.saveByAccount(account);
        return ResponseEntity.ok().body(null);
    }

    /**
     * Return list of movements for {@param uuid} by thymeleaf
     */
    @RequestMapping(value = "/api/movement/list", method = GET)
    public String movements(@RequestParam("uuid") String uuid, Model model) {
        log.debug("Try to find movement by bill uuid : " + uuid);
        Bill bill = billService.findByUuid(uuid);
        model.addAttribute("movements", movementService.findByBill(bill));
        return "included/movements :: movementsList";
    }

    /**
     * Set bill by {@param uuid} as current bill
     */
    @RequestMapping(value = "/api/bill/set_as_current", method = POST)
    @ResponseBody
    public void setCurrent(@RequestParam("uuid") String uuid) {
        log.debug("Set bill :" + uuid + " as current");
        billService.setCurrent(uuid);
    }

    /**
     * @return private room
     */
    @RequestMapping(value = "/private", method = GET)
    public String privateArea(Model model, Principal principal) {
        String name = principal.getName();
        log.debug("User : " + name + " is entering into private area");
        Account account = accountService.findByUsername(name);
        log.debug("Load account : " + account);
        List<Bill> bills = billService.findByAccount(account);
        log.debug("Load bills by account  : " + bills);
        model.addAttribute("account", account);
        model.addAttribute("bills", bills);
        return "private";
    }


}
