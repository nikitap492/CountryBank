package com.bank.controllers;

import com.bank.domain.Account;
import com.bank.domain.other_services.Message;
import com.bank.service.AccountService;
import com.bank.service.MessageService;
import com.bank.validators.MessageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageValidator validator;

    /**
     * @param message will be saved in database
     *                Whether user is authorized or not by {@param authentication}
     * @return {@link ResponseEntity<String>} status and message
     */
    @RequestMapping(value = "/api/message/save", method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveMessage(@RequestBody Message message, Authentication authentication) {
        //validation
        if (authentication != null) {
            Account account = accountService.findByUsername(authentication.getName());
            message = new Message(account, message.getMessage());
        }
        String error = validator.validate(message);
        if (error != null) {
            log.debug("Message was incorrect : " + message);
            return ResponseEntity.badRequest().body(error);
        }
        log.debug("Saving message : " + message);
        //saving
        messageService.save(message);
        return ResponseEntity.ok().body(null);
    }


}
