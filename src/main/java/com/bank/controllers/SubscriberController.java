package com.bank.controllers;

import com.bank.domain.Account;
import com.bank.domain.other_services.Subscriber;
import com.bank.service.AccountService;
import com.bank.service.SubscribeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bank.validators.Validator.validateEmail;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor
public class SubscriberController {
    private final AccountService accountService;
    private final SubscribeService subscribeService;

    /**
     * @param authentication needs for username
     * @return current bill for account
     */
    @ModelAttribute("account")
    public Account account(Authentication authentication) {
        return authentication == null ? null : accountService.findByUsername(authentication.getName());
    }

    /**
     * Saving new anonymous subscribed or  {@param account}
     */
    @RequestMapping(value = "/api/subscriber/add", method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveSubscriber(@RequestBody Subscriber subscriber, @ModelAttribute("account") Account account) {
        if (account != null) {
            subscriber = new Subscriber(account);
        }
        if (!validateEmail(subscriber.getEmail())) {
            log.debug("Bad email: " + subscriber.getEmail());
            return ResponseEntity.badRequest().body("Wrong email");
        }
        subscribeService.save(subscriber);
        log.debug(subscriber + " has been saved");
        return ResponseEntity.ok().body("You have subscribed");
    }

    /**
     * Method delete subscribed {@param account}
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/subscriber/delete", method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public void deleteSubscriber(@ModelAttribute("account") Account account) {
        log.debug("Trying to delete subscriber by account: " + account);
        subscribeService.deleteByAccount(account);
    }

    /**
     * Method check whether {@param account} is subscribed
     *
     * @return {@link ResponseEntity<String>} status and message
     */
    @RequestMapping(value = "/api/subscriber/check", method = GET)
    public ResponseEntity<String> saveSubscriber(Account account) {
        log.debug("Checking: is account " + account + " is subscribed?");
        if (account != null && subscribeService.accountIsSubscribed(account)) return ResponseEntity.ok(null);
        else return ResponseEntity.status(NOT_FOUND).body(null);
    }

}
